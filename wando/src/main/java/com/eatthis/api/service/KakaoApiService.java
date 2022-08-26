package com.eatthis.api.service;

import com.eatthis.web.location.domain.LocationCategory;
import com.eatthis.web.location.domain.LocationCategoryData;
import com.eatthis.web.location.dto.KakaoSearchImageDto;
import com.eatthis.web.location.dto.KakaoSearchImageResponseDto;
import com.eatthis.web.location.dto.KakaoSearchResponseDto;
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

    public List<KakaoSearchResponseDto> getStoresByRectangle(String keyword, LocationCategoryData category, String rect) {
        if (rect.split(",").length != 4) {
            throw new IllegalStateException(String.format("위치 데이터는 좌측의 lng,lat / 우측의 lng,lat 4개가 필요합니다. 요청 데이터: [%s]", rect));
        }

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

    private void searchAllStoreInRange(UriComponentsBuilder uriComponentsBuilder, List<KakaoSearchResponseDto> accData) {
        URI uri = uriComponentsBuilder
                .encode(StandardCharsets.UTF_8)
                .build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + KAKAO_REST_API_KEY);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, new HttpEntity<>(null, headers), Object.class);
        Map<String, Object> bodyMap = objectMapper.convertValue(responseEntity.getBody(), new TypeReference<>() {});
        List<Map<String, String>> documents = objectMapper.convertValue(bodyMap.get("documents"), new TypeReference<>() {});
        Map<String, Object> meta = objectMapper.convertValue(bodyMap.get("meta"), new TypeReference<>() {});

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
                bodyMap = objectMapper.convertValue(responseEntity.getBody(), new TypeReference<>() {});
                documents = objectMapper.convertValue(bodyMap.get("documents"), new TypeReference<>() {});
                meta = objectMapper.convertValue(bodyMap.get("meta"), new TypeReference<>() {});
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
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(KAKAO_HOST + "/v2/search/image");
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + KAKAO_REST_API_KEY);

        List<KakaoSearchImageDto> results = new ArrayList<>();
        for (String storeName : storeNames) {
            URI uri = uriComponentsBuilder.replaceQueryParam("query", storeName)
                    .encode(StandardCharsets.UTF_8)
                    .build().toUri();
            ResponseEntity<Object> responseEntity = restTemplate.exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(null, headers), Object.class);
            Map<String, Object> bodyMap = objectMapper.convertValue(responseEntity.getBody(), new TypeReference<>() {});
            List<Map<String, String>> documents = objectMapper.convertValue(bodyMap.get("documents"), new TypeReference<>() {});
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

        return KakaoSearchImageResponseDto.builder()
                .searchImageData(results)
                .build();
    }
}
