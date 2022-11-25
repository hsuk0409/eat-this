package com.eatthis.domain.alarms;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public enum MyDayOfWeek {

    MON, TUE, WED, THUR, FRI, SAT, SUN

}
