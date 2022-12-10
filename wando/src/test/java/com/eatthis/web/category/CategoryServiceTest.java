package com.eatthis.web.category;

import com.eatthis.api.kakao.KakaoApiService;
import com.eatthis.web.category.dto.CategoryByStep;
import com.eatthis.web.location.domain.LocationCategoryData;
import com.eatthis.web.location.dto.KakaoSearchResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    KakaoApiService kakaoApiService;

    @Autowired
    CategoryService categoryService;

    @DisplayName("카테고리를 세부단계까지 부모키로 세분화한다.")
    @Test
    void separateCategoryStepTest() {
        //given
        LocationCategoryData food = LocationCategoryData.FOOD;
        String lng = "127.04138946025049";
        String lat = "37.51805678063371";
        int radius = 500;
        List<KakaoSearchResponseDto> categories = kakaoApiService
                .getStoresByCircle(food.getDescription(), food, lng, lat, radius);

        //when
        List<CategoryByStep> categoriesByStep = categoryService.subdivideCategoriesByStep(categories);
        Map<String, Integer> sizeData = new HashMap<>();
        sizeData.put("size", 0);
        getCategorizedSearchDataSizeRecursion(categoriesByStep, sizeData);

        //then
        assertThat(categories.size()).isEqualTo(sizeData.get("size"));
    }

    private void getCategorizedSearchDataSizeRecursion(List<CategoryByStep> categoriesByStep,
                                                      Map<String, Integer> sizeData) {
        categoriesByStep.forEach(category -> {
            sizeData.put("size", sizeData.get("size") + category.getKakaoSearchResponseDtos().size());
            if (category.getSubSteps().size() > 0) {
                getCategorizedSearchDataSizeRecursion(category.getSubSteps(), sizeData);
            }
        });
    }

    @DisplayName("카테고리로 세분화 시 빈 데이터 보내도 정상 작동한다.")
    @Test
    void separateCategoryStepWhenSendEmptyDataTest() {
        //given
        List<KakaoSearchResponseDto> categories = new ArrayList<>();

        //when
        List<CategoryByStep> categoriesByStep = categoryService.subdivideCategoriesByStep(categories);
        Map<String, Integer> sizeData = new HashMap<>();
        sizeData.put("size", 0);
        getCategorizedSearchDataSizeRecursion(categoriesByStep, sizeData);

        //then
        assertThat(categories.size()).isEqualTo(sizeData.get("size"));
    }

    @DisplayName("카테고리로 세분화 시 NULL 데이터 보내도 정상 작동한다.")
    @Test
    void separateCategoryStepWhenSendNullDataTest() {
        //given
        List<CategoryByStep> categoriesByStep = categoryService.subdivideCategoriesByStep(null);

        //when
        Map<String, Integer> sizeData = new HashMap<>();
        sizeData.put("size", 0);
        getCategorizedSearchDataSizeRecursion(categoriesByStep, sizeData);

        //then
        assertThat(categoriesByStep.isEmpty()).isTrue();
    }
}
