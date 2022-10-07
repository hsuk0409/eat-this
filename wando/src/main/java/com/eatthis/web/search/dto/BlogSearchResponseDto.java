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
    private int page;
    private int size;
    private boolean isEnd;

    @Builder
    public BlogSearchResponseDto(List<KakaoSearchBlogDto> blogDtos,
                                 int totalCount, int page, int size, boolean isEnd) {
        this.blogDtos = blogDtos;
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
        this.isEnd = isEnd;
    }

}
