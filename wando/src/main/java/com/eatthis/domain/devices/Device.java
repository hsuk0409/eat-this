package com.eatthis.domain.devices;

import com.eatthis.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Device extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long id;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private DeviceOs os;

    @Column(nullable = false)
    private String version;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String fcmToken;

    @Builder
    public Device(DeviceOs os, String version, String type, String fcmToken) {
        this.os = os;
        this.version = version;
        this.type = type;
        this.fcmToken = fcmToken;
    }

}
