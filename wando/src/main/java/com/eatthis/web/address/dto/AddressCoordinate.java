package com.eatthis.web.address.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressCoordinate {

    private String addressName;
    private String longitude;
    private String latitude;

    @Builder
    public AddressCoordinate(String addressName, String longitude, String latitude) {
        this.addressName = addressName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

}
