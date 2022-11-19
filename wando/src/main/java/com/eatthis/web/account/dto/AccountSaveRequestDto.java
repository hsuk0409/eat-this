package com.eatthis.web.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSaveRequestDto {

    private String nickname;
    private String email;
    private Integer phone;
    private String picture;
    @NotNull(message = "기기 정보는 필수값입니다.")
    private DeviceDto device;

}
