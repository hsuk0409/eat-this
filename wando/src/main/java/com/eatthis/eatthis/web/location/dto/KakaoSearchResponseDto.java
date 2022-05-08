package com.eatthis.eatthis.web.location.dto;

import com.eatthis.eatthis.web.location.domain.LocationCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoSearchResponseDto {

    private String placeId;
    private String placeName;
    private String address;
    private String roadAddress;
    private String lng; // x, 경도
    private String lat; // y, 위도
    private String distance; // x,y 존재할 경우 중심 좌표까지의 거리
    private String phoneNumber;
    private LocationCategory category;

    @Builder
    public KakaoSearchResponseDto(String placeId, String placeName, String address, String roadAddress,
                                  String lng, String lat, String distance, String phoneNumber, LocationCategory category) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.address = address;
        this.roadAddress = roadAddress;
        this.lng = lng;
        this.lat = lat;
        this.distance = distance;
        this.phoneNumber = phoneNumber;
        this.category = category;
    }

}
