package com.eatthis.web.location.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoSearchImageDto {

    private String storeName;
    private List<String> imageUrls = new ArrayList<>();

    @Builder
    public KakaoSearchImageDto(String storeName, List<String> imageUrls) {
        this.storeName = storeName;
        this.imageUrls = imageUrls;
    }
}
