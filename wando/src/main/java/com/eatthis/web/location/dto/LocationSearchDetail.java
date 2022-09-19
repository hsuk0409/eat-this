package com.eatthis.web.location.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class LocationSearchDetail {

    private List<KakaoSearchImageDto> imageDtos = new ArrayList<>();
    private String storeName;
    private String categoryName;
    // TODO 후기(사실상 블로그) 갯수 표시해야 되는데 정보 다 가져올지 갯수만 카운팅 할지 고민

    @Builder
    public LocationSearchDetail(List<KakaoSearchImageDto> imageDtos, String storeName, String categoryName) {
        this.imageDtos = imageDtos == null ? new ArrayList<>() : imageDtos;
        this.storeName = storeName;
        this.categoryName = categoryName;
    }

}
