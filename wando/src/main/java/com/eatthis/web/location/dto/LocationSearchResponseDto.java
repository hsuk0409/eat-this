package com.eatthis.web.location.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class LocationSearchResponseDto {

    private List<LocationSearchDetail> searchDetails = new ArrayList<>();
    private int page;
    private int size;

    @Builder
    public LocationSearchResponseDto(List<LocationSearchDetail> searchDetails, int page, int size) {
        this.searchDetails = searchDetails == null ? new ArrayList<>() : searchDetails;
        this.page = page;
        this.size = size;
    }

}
