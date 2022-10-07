package com.eatthis.api.service;

import com.eatthis.web.search.dto.BlogSearchResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KakaoApiServiceTest {

    @Autowired
    private KakaoApiService kakaoApiService;

    @DisplayName("특정 키워드에 대한 블로그 정보를 카카오 API 통해 가져온다.")
    @Test
    void getBlogsByKakaoApi() {
        //given
        String placeName = "파리바게트";
        String address = "경기도 광주시 쌍령동";

        //when
        BlogSearchResponseDto blogResponseDto = kakaoApiService.getBlogsByKakaoApi(placeName, address, 1, 10);

        //then
        assertThat(blogResponseDto.getTotalCount()).isNotZero();
        assertThat(blogResponseDto.getBlogDtos().size()).isLessThanOrEqualTo(10);
        assertThat(blogResponseDto.isEnd()).isFalse();
    }

    @DisplayName("마지막 페이지 조회했을 때 is_end 값 true로 온다.")
    @Test
    void checkNoResponseData() {
        //given
        String placeName = "파리바게트";
        String address = "경기도 광주시 쌍령동";

        //when
        BlogSearchResponseDto blogResponseDto = kakaoApiService.getBlogsByKakaoApi(placeName, address, 4, 10);

        //then
        assertThat(blogResponseDto.getBlogDtos().size()).isLessThanOrEqualTo(10);
        assertThat(blogResponseDto.isEnd()).isTrue();
    }

}