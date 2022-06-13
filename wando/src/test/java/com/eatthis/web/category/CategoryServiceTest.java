package com.eatthis.web.category;

import com.eatthis.api.service.KakaoApiService;
import com.eatthis.web.location.domain.LocationCategoryData;
import com.eatthis.web.location.dto.KakaoSearchResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    KakaoApiService kakaoApiService;

    @Autowired
    CategoryService categoryService;

    @DisplayName("카테고리를 세부단계까지 부모키로 세분화한다.")
    @Test
    void separateCategoryStep() {
        //given
        LocationCategoryData food = LocationCategoryData.FOOD;
        String lng = "127.04138946025049";
        String lat = "37.51805678063371";
        int radius = 500;
        List<KakaoSearchResponseDto> categories = kakaoApiService
                .getStoresByCircle(food.getDescription(), food, lng, lat, radius);

        //when
        System.out.println(radius);
        

        //then
    }
}
