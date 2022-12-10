package com.eatthis.web.location;

import com.eatthis.api.kakao.KakaoApiService;
import com.eatthis.web.location.dto.KakaoSearchImageResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KakaoSearchImageTest {

    @Autowired
    private KakaoApiService kakaoApiService;

    @DisplayName("경기광주역에 대한 이미지를 카카오 이미지 검색 API를 통해 가져온다.")
    @Test
    void kakaoSearchImageTest() {
        //given
        List<String> storeNames = List.of("경기광주역");

        //when
        KakaoSearchImageResponseDto result = kakaoApiService.getImagesByKeyword(storeNames);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getSearchImageData()).isNotEmpty();
        assertThat(result.getSearchImageData().size()).isLessThan(11);
    }

}
