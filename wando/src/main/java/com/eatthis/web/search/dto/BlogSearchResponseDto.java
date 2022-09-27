package com.eatthis.web.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BlogSearchResponseDto {

    private List<KakaoSearchBlogDto> blogDtos = new ArrayList<>();
    private int totalCount;

    @Builder
    public BlogSearchResponseDto(List<KakaoSearchBlogDto> blogDtos,
                                 int totalCount) {
        this.blogDtos = blogDtos;
        this.totalCount = totalCount;
    }

}
