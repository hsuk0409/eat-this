package com.eatthis.web.category.dto;

import com.eatthis.web.location.dto.KakaoSearchResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryByStep {

    private String categoryName;
    private int step;
    private List<KakaoSearchResponseDto> kakaoSearchResponseDtos = new ArrayList<>();
    private CategoryByStep subStep;
    private boolean lastLevel;

    @Builder
    public CategoryByStep(String categoryName, int step, CategoryByStep subStep) {
        this.categoryName = categoryName;
        this.step = step;
        this.subStep = subStep;
    }

    public void addAllSearchData(List<KakaoSearchResponseDto> searchResponseDtos) {
        this.kakaoSearchResponseDtos.addAll(searchResponseDtos);
    }

    public void isLastLevel(boolean lastLevel) {
        this.lastLevel = lastLevel;
    }

}
