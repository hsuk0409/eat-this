package com.eatthis.eatthis.web.location.domain;

import lombok.Getter;

@Getter
public enum LocationCategoryData {

    FOOD("FD6", "음식점")
    ;

    private final String code;
    private final String description;

    LocationCategoryData(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
