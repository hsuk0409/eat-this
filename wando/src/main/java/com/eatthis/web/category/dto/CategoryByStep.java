package com.eatthis.web.category.dto;

import com.eatthis.web.location.dto.KakaoSearchResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class CategoryByStep {

    private String categoryName;
    private int step;
    private final List<KakaoSearchResponseDto> kakaoSearchResponseDtos = new ArrayList<>();
    private final List<CategoryByStep> subSteps = new ArrayList<>();
    private boolean lastLevel;

    @Builder
    public CategoryByStep(String categoryName, int step, boolean lastLevel) {
        this.categoryName = categoryName;
        this.step = step;
        this.lastLevel = lastLevel;
    }

    public void addAllSearchData(List<KakaoSearchResponseDto> searchResponseDtos) {
        this.kakaoSearchResponseDtos.addAll(searchResponseDtos);
    }

    public void isLastLevel(boolean lastLevel) {
        this.lastLevel = lastLevel;
    }

    public void updateSubStepCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public void addSubStepCategory(CategoryByStep subStepCategory) {
        List<CategoryByStep> subSteps = this.subSteps;
        Optional<CategoryByStep> categoryOptional = subSteps.stream()
                .filter(category -> !ObjectUtils.isEmpty(category))
                .filter(category -> category.getCategoryName().equals(subStepCategory.getCategoryName()))
                .findAny();
        if (categoryOptional.isEmpty()) {
            subSteps.add(subStepCategory);
        }
    }
}
