package com.eatthis.web.account.dto;

import com.eatthis.domain.devices.DeviceOs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {

    @NotNull(message = "기기의 OS 정보를 필수입니다.")
    private DeviceOs os;
    @NotNull(message = "기기의 OS 버전 정보는 필수입니다.")
    private String version;
    @NotNull(message = "기종 정보는 필수입니다.")
    private String type;
    @NotNull(message = "fcmToken 정보는 필수입니다.")
    private String fcmToken;

}
