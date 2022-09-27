package com.eatthis.web.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoSearchBlogDto {

    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String thumbnail;

    @Builder
    public KakaoSearchBlogDto(String title, String contents,
                              String url, String blogName,
                              String thumbnail) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.blogName = blogName;
        this.thumbnail = thumbnail;
    }
}
