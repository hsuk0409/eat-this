package com.eatthis.api.firebase;

import com.eatthis.api.firebase.dto.FcmMessage;
import com.eatthis.api.firebase.dto.FcmMessageContents;
import com.eatthis.api.firebase.dto.FcmNotification;
import com.eatthis.handler.exception.CustomException;
import com.eatthis.handler.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseMessageService {

    private final String API_URL = "https://fcm.googleapis.com/v1/products/android-/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String fcmToken, String title, String contents) throws IOException {
        String message = makeMessage(fcmToken, title, contents);

        OkHttpClient client = new OkHttpClient();
        String mediaType = MediaType.APPLICATION_JSON_VALUE;
        RequestBody requestBody = RequestBody.create(
                message,
                okhttp3.MediaType.parse(mediaType)
        );
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, mediaType)
                .build();

        Response response = client.newCall(request).execute();
        int responseCode = response.code();
        String responseMessage = response.message();
        if (responseCode != 200) {
            log.info(String.format("[fcm transfer error] code: %d, message: %s", responseCode, responseMessage));
            ErrorCode errorCode = ErrorCode.REQUEST_EXTERNAL_API_ERROR;
            errorCode.setCustomDetail(String.format("FCM 요청 에러!! message: %s", responseMessage));
            throw new CustomException(errorCode);
        } else {
            log.info("fcm transfer success: " + responseMessage);
        }

    }

    private String makeMessage(String fcmToken, String title, String contents) throws IOException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessageContents.builder()
                        .token(fcmToken)
                        .notification(FcmNotification.builder()
                                .title(title)
                                .body(contents)
                                .build())
                        .build())
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String keyPath = "firebase/pawstreet-firebase-key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(keyPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
