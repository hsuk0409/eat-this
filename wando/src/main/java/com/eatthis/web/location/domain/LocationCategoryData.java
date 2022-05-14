package com.eatthis.web.location.domain;

import lombok.Getter;

@Getter
public enum LocationCategoryData {

    SUPERMARKET("MT1", "대형마트"),
    CONVENIENCE_STORE("CS2", "편의점"),
    CAFE("CE7", "카페"),
    FOOD("FD6", "음식점")
    ;

    private final String code;
    private final String description;

    LocationCategoryData(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
