package com.eatthis.web.location.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationCategory {

    private String groupCode;
    private String groupName;
    private String fullName;

    @Builder
    public LocationCategory(String groupCode, String groupName, String fullName) {
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.fullName = fullName;
    }

}
