package com.eatthis.api.service;

import com.eatthis.handler.exception.CustomException;
import com.eatthis.handler.exception.ErrorCode;
import com.eatthis.web.location.domain.LocationCategory;
import com.eatthis.web.location.domain.LocationCategoryData;
import com.eatthis.web.location.dto.KakaoSearchImageDto;
import com.eatthis.web.location.dto.KakaoSearchImageResponseDto;
import com.eatthis.web.location.dto.KakaoSearchResponseDto;
import com.eatthis.web.search.dto.BlogSearchResponseDto;
import com.eatthis.web.search.dto.KakaoSearchBlogDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kakao-rest-api-key}")
    private String KAKAO_REST_API_KEY;
    private final String KAKAO_HOST = "https://dapi.kakao.com";

    public List<KakaoSearchResponseDto> getStoresByCircle(String keyword, LocationCategoryData category,
                                                          String longitude, String latitude, int radius) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(KAKAO_HOST + "/v2/local/search/keyword")
                .queryParam("query", keyword)
                .queryParam("category_group_code", category.getCode())
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .queryParam("radius", radius);

        List<KakaoSearchResponseDto> responseDtos = new ArrayList<>();

        searchAllStoreInRange(uriComponentsBuilder, responseDtos);

        return responseDtos.stream()
                .sorted(Comparator.comparing(KakaoSearchResponseDto::getDistance))
                .collect(Collectors.toList());
    }

    public List<KakaoSearchResponseDto> getStoresPagingByCircle(String keyword, LocationCategoryData category,
                                                                String longitude, String latitude, int radius,
                                                                int page, int size) {
        URI uri = UriComponentsBuilder.fromUriString(KAKAO_HOST + "/v2/local/search/keyword")
                .queryParam("query", keyword)
                .queryParam("category_group_code", category.getCode())
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .queryParam("radius", radius)
                .queryParam("page", page)
                .queryParam("size", size)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();

        List<KakaoSearchResponseDto> responseDtos = new ArrayList<>();

        searchStoresPaging(uri, responseDtos);

        return responseDtos.stream()
                .sorted(Comparator.comparing(KakaoSearchResponseDto::getDistance))
                .collect(Collectors.toList());
    }

    private void searchStoresPaging(URI uri, List<KakaoSearchResponseDto> accData) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + KAKAO_REST_API_KEY);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, new HttpEntity<>(null, headers), Object.class);
        Map<String, Object> bodyMap = objectMapper.convertValue(responseEntity.getBody(), new TypeReference<>() {});
        List<Map<String, String>> documents = objectMapper.convertValue(bodyMap.get("documents"), new TypeReference<>() {});

        convertAndSaveSearchResponseDto(documents, accData);
    }

    public List<KakaoSearchResponseDto> getStoresByRectangle(String keyword, LocationCategoryData category, String rect) {
        validateRectangleCoordinate(rect);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(KAKAO_HOST + "/v2/local/search/keyword")
                .queryParam("query", keyword)
                .queryParam("category_group_code", category.getCode())
                .queryParam("rect", rect);

        List<KakaoSearchResponseDto> responseDtos = new ArrayList<>();

        searchAllStoreInRange(uriComponentsBuilder, responseDtos);

        return responseDtos.stream()
                .sorted(Comparator.comparing(KakaoSearchResponseDto::getDistance))
                .collect(Collectors.toList());
    }

    private void validateRectangleCoordinate(String rect) {
        String[] coordinates = rect.split(",");
        ErrorCode errorCode = ErrorCode.PARAMETER_IS_REQUIRED;
        if (coordinates.length != 4) {
            errorCode.setCustomDetail("사각형 지정 범위에는 ','로 구분해서 (좌)lng,lat,(우)lng,lat 4개의 좌표만 필요합니다.");
            throw new CustomException(errorCode);
        }
    }

    private void searchAllStoreInRange(UriComponentsBuilder uriComponentsBuilder,
                                       List<KakaoSearchResponseDto> accData) {
        URI uri = uriComponentsBuilder
                .encode(StandardCharsets.UTF_8)
                .build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + KAKAO_REST_API_KEY);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, new HttpEntity<>(null, headers), Object.class);
        Map<String, Object> bodyMap = objectMapper.convertValue(responseEntity.getBody(), new TypeReference<>() {
        });
        List<Map<String, String>> documents = objectMapper.convertValue(bodyMap.get("documents"), new TypeReference<>() {
        });
        Map<String, Object> meta = objectMapper.convertValue(bodyMap.get("meta"), new TypeReference<>() {
        });

        convertAndSaveSearchResponseDto(documents, accData);

        Boolean isEnd = (Boolean) meta.get("is_end");
        int page = 2;
        while (!ObjectUtils.isEmpty(isEnd) && !isEnd) {
            uri = uriComponentsBuilder
                    .replaceQueryParam("page", page++)
                    .encode(StandardCharsets.UTF_8)
                    .build().toUri();
            try {
                responseEntity = restTemplate.exchange(
                        uri, HttpMethod.GET, new HttpEntity<>(null, headers), Object.class);
                bodyMap = objectMapper.convertValue(responseEntity.getBody(), new TypeReference<>() {
                });
                documents = objectMapper.convertValue(bodyMap.get("documents"), new TypeReference<>() {
                });
                meta = objectMapper.convertValue(bodyMap.get("meta"), new TypeReference<>() {
                });
            } catch (Exception ex) {
                log.error(String.format("전체 스토어 조회 중 에러 발생!! 메세지: %s", ex.getMessage()));
                continue;
            }

            convertAndSaveSearchResponseDto(documents, accData);

            isEnd = (Boolean) meta.get("is_end");
        }
    }

    private void convertAndSaveSearchResponseDto(List<Map<String, String>> documents, List<KakaoSearchResponseDto> accData) {
        for (Map<String, String> document : documents) {
            accData.add(KakaoSearchResponseDto.builder()
                    .placeId(document.get("id"))
                    .placeName(document.get("place_name"))
                    .phoneNumber(document.get("phone"))
                    .address(document.get("address_name"))
                    .roadAddress(document.get("road_address_name"))
                    .lng(document.get("x"))
                    .lat(document.get("y"))
                    .distance(document.get("distance"))
                    .category(LocationCategory.builder()
                            .groupCode(document.get("category_group_code"))
                            .groupName(document.get("category_group_name"))
                            .fullName(document.get("category_name"))
                            .build())
                    .build());
        }
    }

    public KakaoSearchImageResponseDto getImagesByKeyword(List<String> storeNames) {
        List<KakaoSearchImageDto> results = getKakaoImageApiResults(storeNames);

        return KakaoSearchImageResponseDto.builder()
                .searchImageData(results)
                .build();
    }

    private List<KakaoSearchImageDto> getKakaoImageApiResults(List<String> storeNames) {
        List<KakaoSearchImageDto> results = new ArrayList<>();
        for (String storeName : storeNames) {
            List<Map<String, String>> documents = requestKakaoImageApi(storeName);
            if (!ObjectUtils.isEmpty(documents)) {
                results.add(KakaoSearchImageDto.builder()
                        .storeName(storeName)
                        .imageUrls(documents
                                .stream()
                                .limit(10)
                                .map(doc -> doc.get("image_url"))
                                .collect(Collectors.toList()))
                        .build());
            }
        }

        return results;
    }

    public List<String> getImagesByKakaoApi(String storeName) {
        List<Map<String, String>> documents = requestKakaoImageApi(storeName);
        if (ObjectUtils.isEmpty(documents)) return new ArrayList<>();

        return documents.stream()
                .limit(10)
                .filter(document -> !ObjectUtils.isEmpty(document.get("image_url")))
                .map(document -> document.get("image_url"))
                .collect(Collectors.toList());
    }

    private List<Map<String, String>> requestKakaoImageApi(String storeName) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(KAKAO_HOST + "/v2/search/image");
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + KAKAO_REST_API_KEY);

        URI uri = uriComponentsBuilder.replaceQueryParam("query", storeName)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, new HttpEntity<>(null, headers), Object.class);
        Map<String, Object> bodyMap = objectMapper.convertValue(responseEntity.getBody(), new TypeReference<>() {});

        return objectMapper.convertValue(bodyMap.get("documents"), new TypeReference<>() {});
    }

    public BlogSearchResponseDto getBlogsByKakaoApi(String placeName, String address) {
        URI uri = UriComponentsBuilder.fromUriString(KAKAO_HOST + "/v2/search/blog")
                .queryParam("query", String.format("%s %s", address, placeName))
                .encode(StandardCharsets.UTF_8)
                .build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + KAKAO_REST_API_KEY);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, new HttpEntity<>(null, headers), Object.class);
        Map<String, Object> bodyMap = objectMapper.convertValue(responseEntity.getBody(), new TypeReference<>() {});
        List<Map<String, String>> documentMaps = objectMapper.convertValue(bodyMap.get("documents"), new TypeReference<>() {});
        Map<String, Object> metaMap = objectMapper.convertValue(bodyMap.get("meta"), new TypeReference<>() {});

        try {
            return BlogSearchResponseDto.builder()
                    .blogDtos(documentMaps.stream()
                            .map(document -> KakaoSearchBlogDto.builder()
                                    .title(document.get("title"))
                                    .contents(document.get("contents"))
                                    .url(document.get("url"))
                                    .blogName(document.get("blogname"))
                                    .thumbnail(document.get("thumbnail"))
                                    .build())
                            .collect(Collectors.toList()))
                    .totalCount((Integer) metaMap.get("total_count"))
                    .build();
        } catch (Exception ex) {
            log.error("블로그 검색 데이터 변환 중 에러 발생! " + ex);
            return null;
        }
    }
}
