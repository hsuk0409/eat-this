package com.eatthis.web.category;

import com.eatthis.web.category.dto.CategoryByStep;
import com.eatthis.web.location.domain.LocationCategory;
import com.eatthis.web.location.dto.KakaoSearchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    public List<CategoryByStep> subdivideCategoriesByStep(List<KakaoSearchResponseDto> categories) {
        if (ObjectUtils.isEmpty(categories)) {
            return new ArrayList<>();
        }
        Map<String, List<KakaoSearchResponseDto>> categoriesByStep = new HashMap<>();
        setMapCategoryToStep(categories, categoriesByStep);

        List<CategoryByStep> result = new ArrayList<>();
        setCategoriesAsMappedStepByCategory(categories, categoriesByStep, result);

        return result;
    }

    private void setMapCategoryToStep(List<KakaoSearchResponseDto> categories,
                                      Map<String, List<KakaoSearchResponseDto>> categoriesByStep) {
        categories.forEach(category -> {
            LocationCategory locationCategory = category.getCategory();
            String[] categorySplit = locationCategory.getFullName().split(">");
            for (int i = 1; categorySplit.length > 1 && i < categorySplit.length; ++i) {
                List<KakaoSearchResponseDto> responseDtos = categoriesByStep.get(categorySplit[i].trim());
                if (ObjectUtils.isEmpty(responseDtos)) {
                    responseDtos = new ArrayList<>();
                }
                if (i == (categorySplit.length - 1)) {
                    responseDtos.add(category);
                }
                categoriesByStep.put(categorySplit[i].trim(), responseDtos);
            }
        });
    }

    private void setCategoriesAsMappedStepByCategory(List<KakaoSearchResponseDto> categories,
                                                     Map<String, List<KakaoSearchResponseDto>> categoriesByStep,
                                                     List<CategoryByStep> result) {

        Map<String, CategoryByStep> memoryCheckAlreadySavedCategory = new HashMap<>();

        categories.forEach(category -> {
            LocationCategory locationCategory = category.getCategory();
            String[] categorySplit = locationCategory.getFullName().split(">");
            for (int i = 1; categorySplit.length > 0 && i < categorySplit.length; ++i) {
                String categoryName = categorySplit[i].trim();
                CategoryByStep newCategory = CategoryByStep.builder()
                        .step(i)
                        .categoryName(categoryName)
                        .build();
                CategoryByStep alreadySavedCategory = memoryCheckAlreadySavedCategory.get(categoryName);
                if (ObjectUtils.isEmpty(alreadySavedCategory)) { // 최초 저장 카테고리
                    newCategory.addAllSearchData(categoriesByStep.get(categoryName));
                    memoryCheckAlreadySavedCategory.put(categoryName, newCategory);

                    if (i > 1) {
                        CategoryByStep parentStepCategory = memoryCheckAlreadySavedCategory.get(categorySplit[i - 1].trim());
                        parentStepCategory.addSubStepCategory(newCategory);
                    } else {
                        result.add(newCategory);
                    }
                } else { // 이미 한번이상 저장된 카테고리
                    if (i > 1) { // 1단계 이상일 경우
                        CategoryByStep parentStepCategory = memoryCheckAlreadySavedCategory.get(categorySplit[i - 1].trim());
                        if (!ObjectUtils.isEmpty(parentStepCategory)) {
                            parentStepCategory.addSubStepCategory(newCategory);
                        }
                    }
                }
            }
        });
    }
}
