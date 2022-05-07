package com.eatthis.eatthis.web.location.domain;

import lombok.Getter;

@Getter
public enum LocationCategory {

    FOOD("FD6", "음식점")
    ;

    private final String code;
    private final String description;

    LocationCategory(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
