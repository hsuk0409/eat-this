package com.eatthis.api.service;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kakao-rest-api-key}")
    private String KAKAO_REST_API_KEY;
    private final String KAKAO_HOST = "https://dapi.kakao.com";


    public List<Map<String, Object>> getCoordinateByTown(String town) {
        URI uri = UriComponentsBuilder.fromUriString(KAKAO_HOST + "/v2/local/search/address")
                .queryParam("query", town)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + KAKAO_REST_API_KEY);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, new HttpEntity<>(null, headers), Object.class);
        Map<String, Object> bodyMap = objectMapper.convertValue(responseEntity.getBody(), new TypeReference<>() {});
        return objectMapper.convertValue(bodyMap.get("documents"), new TypeReference<>() {});
    }
}
