package com.eatthis.web.location.dto;

import com.eatthis.web.search.dto.BlogSearchResponseDto;
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
    private String address;
    private List<String> images = new ArrayList<>();
    private BlogSearchResponseDto blogInfo;

    @Builder
    public LocationSearchDetail(String storeName, String categoryName, String address,
                                List<String> images,
                                BlogSearchResponseDto blogResponseDto) {
        this.storeName = storeName;
        this.categoryName = categoryName;
        this.address = address;
        this.images = images == null ? new ArrayList<>() : images;
        this.blogInfo = blogResponseDto;
    }

}
