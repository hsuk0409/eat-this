package com.eatthis.web.location.dto;

import com.eatthis.web.location.domain.LocationCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Getter
@NoArgsConstructor
public class KakaoSearchResponseDto {

    private String placeId;
    private String placeName;
    private String address;
    private String roadAddress;
    private String eupMyeonDong;
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
        this.eupMyeonDong = setEupMyeonDongByAddress(address);
        this.lng = lng;
        this.lat = lat;
        this.distance = distance;
        this.phoneNumber = phoneNumber;
        this.category = category;
    }

    private String setEupMyeonDongByAddress(String address) {
        if (ObjectUtils.isEmpty(address)) {
            return "내주변"; // 혹시나 주소 없을 경우 기존 텍스트 반환
        }

        // TODO Biz logic
        
        return null;
    }

}
