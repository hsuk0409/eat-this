package com.eatthis.domain.alarms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {

    SOUND("소리"),
    VIBRATION("진동"),
    SLIENCE("무음")
    ;

    private final String description;

}
