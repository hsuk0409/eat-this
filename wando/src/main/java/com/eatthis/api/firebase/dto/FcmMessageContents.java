package com.eatthis.api.firebase.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmMessageContents {

    private FcmNotification notification;
    private String token;

    @Builder
    public FcmMessageContents(FcmNotification notification, String token) {
        this.notification = notification;
        this.token = token;
    }
}
