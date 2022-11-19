package com.eatthis.domain.accounts;

import com.eatthis.domain.BaseTimeEntity;
import com.eatthis.domain.alarms.Alarm;
import com.eatthis.domain.devices.Device;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(length = 20)
    private String nickname;

    @Column
    private String email;

    @Column(length = 10)
    private Integer phone;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column
    private LocalDateTime lastLoginDateTime;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "device_id", unique = true)
    private Device device;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarms = new ArrayList<>();

    @Builder
    public Account(String nickname, String email, Integer phone,
                   String picture, UserRole role, Device device) {
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.picture = picture;
        this.role = role;
        this.device = device;
        this.lastLoginDateTime = LocalDateTime.now();
    }

    public void addAlarm(Alarm alarm) {
        this.alarms.add(alarm);
        alarm.updateAccount(this);
    }

    public void updateLastLoginDateTime() {
        this.lastLoginDateTime = LocalDateTime.now();
    }
}
