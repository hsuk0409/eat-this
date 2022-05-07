package com.eatthis.eatthis.web.api.service;

import com.eatthis.eatthis.web.location.domain.LocationCategory;
import com.eatthis.eatthis.web.location.dto.KakaoSearchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoApiService {

    @Value("${kakao-rest-api-key}")
    private String KAKAO_REST_API_KEY;


    public KakaoSearchResponseDto getStoresByCircle(String keyword, LocationCategory category,
                                                    String longitude, String latitude, int radius) {
        return null;
    }
}
