package com.eatthis.web.location.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoSearchImageResponseDto {

    List<KakaoSearchImageDto> searchImageData = new ArrayList<>();

    @Builder
    public KakaoSearchImageResponseDto(List<KakaoSearchImageDto> searchImageData) {
        this.searchImageData = searchImageData;
    }
}
