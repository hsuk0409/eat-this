package com.eatthis.web.location;

import com.eatthis.api.kakao.KakaoApiService;
import com.eatthis.web.location.domain.LocationCategoryData;
import com.eatthis.web.location.dto.KakaoSearchResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LocationRectangleServiceTest {

    @Autowired
    private KakaoApiService kakaoApiService;

    @DisplayName("사각형 범위의 위치로 주변 음식점 정보를 가져온다.")
    @Test
    void getStoresByRectangleRangeTest() {
        //given
        String rect = "127.112597264,37.398321708,127.108383849,37.404448936";
        LocationCategoryData foodCategory = LocationCategoryData.FOOD;

        //when
        List<KakaoSearchResponseDto> storesByRectangle = kakaoApiService
                .getStoresByRectangle(foodCategory.getDescription(), foodCategory, rect);

        //then
        assertThat(storesByRectangle).isNotEmpty();
        // TODO 해당 위치 데이터 주소로 제대로 가져오는지 확인 필요
    }

}
