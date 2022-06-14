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
        List<CategoryByStep> result = new ArrayList<>();
        Map<String, List<KakaoSearchResponseDto>> categoriesByStep = new HashMap<>();
        setMapCategoryToStep(categoriesByStep, categories);


        return result;
    }

    private void setMapCategoryToStep(Map<String, List<KakaoSearchResponseDto>> categoriesByStep,
                                      List<KakaoSearchResponseDto> categories) {
        categories.forEach(category -> {
            LocationCategory locationCategory = category.getCategory();
            String[] categorySplit = locationCategory.getFullName().split(">");
            for (int i = 1; categorySplit.length > 1 && i < categorySplit.length; ++i) {
                List<KakaoSearchResponseDto> responseDtos = categoriesByStep.get(categorySplit[i]);
                if (!ObjectUtils.isEmpty(responseDtos)) {
                    responseDtos.add(category);
                }
                categoriesByStep.put(categorySplit[i], responseDtos);
            }
        });
    }
}
