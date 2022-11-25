package com.eatthis.domain.alarms;

import com.eatthis.domain.BaseTimeEntity;
import com.eatthis.domain.accounts.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ElementCollection(targetClass = MyDayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "dayOfWeeks")
    private Set<MyDayOfWeek> dayOfWeeks = new LinkedHashSet<>();

    @Column(nullable = false)
    private int minutes;

    @Column(nullable = false)
    private int seconds;

    @Column(nullable = false)
    private Boolean usable;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmType alarmType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public Alarm(Set<MyDayOfWeek> dayOfWeeks, int minutes, int seconds,
                 Boolean usable, AlarmType alarmType, Account account) {
        this.dayOfWeeks = dayOfWeeks;
        this.minutes = minutes;
        this.seconds = seconds;
        this.usable = usable;
        this.alarmType = alarmType;
        this.account = account;
    }

    public void updateAccount(Account account) {
        this.account = account;
    }

}
