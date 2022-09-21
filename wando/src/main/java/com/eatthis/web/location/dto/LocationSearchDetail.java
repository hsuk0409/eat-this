package com.eatthis.web.location.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class LocationSearchDetail {

    private String storeName;
    private String categoryName;
    private List<String> images = new ArrayList<>();
    // TODO 후기(사실상 블로그) 갯수 표시해야 되는데 정보 다 가져올지 갯수만 카운팅 할지 고민

    @Builder
    public LocationSearchDetail(String storeName, String categoryName,
                                List<String> images) {
        this.storeName = storeName;
        this.categoryName = categoryName;
        this.images = images == null ? new ArrayList<>() : images;
    }

}
