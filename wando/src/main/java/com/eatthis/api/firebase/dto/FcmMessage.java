package com.eatthis.api.firebase.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmMessage {

    private boolean validateOnly;
    private FcmMessageContents message;

    @Builder
    public FcmMessage(boolean validateOnly, FcmMessageContents message) {
        this.validateOnly = validateOnly;
        this.message = message;
    }
}
