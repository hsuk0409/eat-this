package com.eatthis.domain.accounts;

import com.eatthis.domain.BaseTimeEntity;
import com.eatthis.domain.alarms.Alarm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(length = 10)
    private String nickname;

    @Column
    private String email;

    @Column(length = 10)
    private Integer phone;

    @Column
    private String picture;

    @Column(nullable = false)
    private String fcmToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarms = new ArrayList<>();

    @Builder
    public Account(String nickname, String email, Integer phone,
                   String picture, String fcmToken, UserRole role) {
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.fcmToken = fcmToken;
        this.picture = picture;
        this.role = role;
    }

    public void addAlarm(Alarm alarm) {
        this.alarms.add(alarm);
        alarm.updateAccount(this);
    }

}
