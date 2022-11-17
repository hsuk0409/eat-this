package com.eatthis.domain.alarms;

import com.eatthis.domain.BaseTimeEntity;
import com.eatthis.domain.accounts.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @Column
    private LocalTime startTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public Alarm(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void updateAccount(Account account) {
        this.account = account;
    }

}
