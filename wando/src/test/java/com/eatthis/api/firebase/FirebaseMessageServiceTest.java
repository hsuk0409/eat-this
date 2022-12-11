package com.eatthis.api.firebase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FirebaseMessageServiceTest {

    @Autowired
    private FirebaseMessageService firebaseMessageService;

    @DisplayName("임의의 fcm 토큰으로 푸시 알림을 성공적으로 보낸다.")
    @Test
    void sendFcmMessage() throws IOException {
        //given
        String fcmToken = "";
        String title = "";
        String contents = "";

        //when & then
        firebaseMessageService.sendMessageTo(fcmToken, title, contents);
    }

}