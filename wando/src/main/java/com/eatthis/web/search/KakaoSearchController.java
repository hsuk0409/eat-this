package com.eatthis.web.search;

import com.eatthis.api.kakao.KakaoApiService;
import com.eatthis.web.search.dto.BlogSearchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoSearchController {

    private final KakaoApiService kakaoApiService;

    @GetMapping("/search/blogs")
    public ResponseEntity<BlogSearchResponseDto> getBlogsUsingKakaoSearchApi(@RequestParam(value = "placeName") String placeName,
                                                                             @RequestParam(value = "address") String address,
                                                                             @RequestParam(value = "page") int page,
                                                                             @RequestParam(value = "size") int size) {

        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 10;
        }

        return ResponseEntity.ok().body(kakaoApiService.getBlogsByKakaoApi(placeName, address, page, size));

    }

}
