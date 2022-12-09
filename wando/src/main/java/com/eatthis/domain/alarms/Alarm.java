package com.eatthis.domain.alarms;

import com.eatthis.domain.BaseTimeEntity;
import com.eatthis.domain.accounts.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Entity
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @Column(nullable = false)
    private String daysOfWeek;

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
    public Alarm(String daysOfWeek, int minutes, int seconds,
                 Boolean usable, AlarmType alarmType, Account account) {
        this.daysOfWeek = daysOfWeek;
        this.minutes = minutes;
        this.seconds = seconds;
        this.usable = usable;
        this.alarmType = alarmType;
        this.account = account;
    }

    public void updateAccount(Account account) {
        this.account = account;
    }

    public void addDayOfWeek(String daysOfWeek) {
        if (this.daysOfWeek.contains(daysOfWeek)) return;

        this.daysOfWeek += String.format(",%s", daysOfWeek);
    }

    public List<String> getDaysOfWeek() {
        if (this.daysOfWeek == null) return new ArrayList<>();

        String[] daysOfWeek = this.daysOfWeek.split(",");
        return Arrays.stream(daysOfWeek).collect(Collectors.toList());
    }

}
