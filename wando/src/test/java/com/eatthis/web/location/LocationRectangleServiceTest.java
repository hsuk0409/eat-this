package com.eatthis.web.location;

import com.eatthis.api.service.KakaoApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationRectangleServiceTest {

    @Autowired
    private KakaoApiService kakaoApiService;

    @DisplayName("사각형 범위의 위치로 주변 음식점 정보를 가져온다.")
    @Test
    void getStoresByRectangleRangeTest() {
        //given
        //when
        //then
    }

}
