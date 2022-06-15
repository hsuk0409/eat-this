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
                List<KakaoSearchResponseDto> responseDtos = categoriesByStep.get(categorySplit[i]);
                if (ObjectUtils.isEmpty(responseDtos)) {
                    responseDtos = new ArrayList<>();
                }
                if (i == (categorySplit.length - 1)) {
                    responseDtos.add(category);
                }
                categoriesByStep.put(categorySplit[i], responseDtos);
            }
        });
    }

    private void setCategoriesAsMappedStepByCategory(List<KakaoSearchResponseDto> categories,
                                                     Map<String, List<KakaoSearchResponseDto>> categoriesByStep,
                                                     List<CategoryByStep> result) {
        Map<String, CategoryByStep> alreadySaveMemory = new HashMap<>();

        categories.forEach(category -> {
            LocationCategory locationCategory = category.getCategory();
            String[] categorySplit = locationCategory.getFullName().split(">");
            CategoryByStep categoryByStep;
            for (int i = 1; categorySplit.length > 1 && i < categorySplit.length; ++i) {
                String detailCategory = categorySplit[i];
                


            }
        });
    }
}
