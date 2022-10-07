package com.eatthis.web.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Getter
@NoArgsConstructor
public class KakaoSearchBlogDto {

    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String thumbnail;
    private String date;

    @Builder
    public KakaoSearchBlogDto(String title, String contents,
                              String url, String blogName,
                              String thumbnail, String createdDateTimeString) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.blogName = blogName;
        this.thumbnail = thumbnail;
        LocalDateTime createdDateTime = LocalDateTime.parse(createdDateTimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        LocalDate createdDate = createdDateTime.toLocalDate();
        String year = String.valueOf(createdDate.getYear());
        String month = String.valueOf(createdDate.getMonthValue());
        String day = String.valueOf(createdDate.getDayOfMonth());
        DayOfWeek dayOfWeek = createdDate.getDayOfWeek();
        String displayName = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREA);
        this.date = String.format("%s.%s.%s %s", year, month, day, displayName);
    }
}
