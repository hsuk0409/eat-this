package com.eatthis.api.firebase.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmNotification {

    private String title;
    private String body;
    private String image;

    @Builder
    public FcmNotification(String title, String body, String image) {
        this.title = title;
        this.body = body;
        this.image = image;
    }
}
