package com.eatthis.web.location.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KakaoSearchResponseDtoTest {

    @DisplayName("주소에서 읍면동 추출 시 읍, 면, 동 존재할 시 필드를 설정한다.")
    @Test
    void convertAddressTest() {
        //given
        String address = "서울시 마포구 흑석2동 123-1";

        //when
        KakaoSearchResponseDto responseDto = KakaoSearchResponseDto.builder()
                .address(address)
                .build();

        //then
        assertThat(responseDto.getEupMyeonDong()).isEqualTo("흑석2동");
    }

}