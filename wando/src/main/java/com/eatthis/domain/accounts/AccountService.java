package com.eatthis.domain.accounts;

import com.eatthis.domain.devices.Device;
import com.eatthis.domain.devices.DeviceRepository;
import com.eatthis.handler.exception.CustomException;
import com.eatthis.handler.exception.ErrorCode;
import com.eatthis.utils.UUIDGenerateUtils;
import com.eatthis.web.account.dto.AccountSaveRequestDto;
import com.eatthis.web.account.dto.DeviceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final DeviceRepository deviceRepository;

    @Transactional
    public Long saveIfNewAccount(AccountSaveRequestDto requestDto) {
        DeviceDto deviceDto = requestDto.getDevice();
        Optional<Device> deviceOptional = deviceRepository.findByFcmToken(deviceDto.getFcmToken());
        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            Account account = getAccountByDeviceId(device.getId());
            if (account != null) {
                account.updateLastLoginDateTime();
                return account.getId();
            }
        }

        Device device = Device.builder()
                .os(deviceDto.getOs())
                .version(deviceDto.getVersion())
                .type(deviceDto.getType())
                .fcmToken(deviceDto.getFcmToken())
                .build();
        Device savedDevice = saveAndFindNewDevice(device);

        String requestNickName = requestDto.getNickname();
        String nickname = ObjectUtils.isEmpty(requestNickName) ?
                UUIDGenerateUtils.makeShorUUID(6) : requestNickName;
        Account account = Account.builder()
                .nickname(nickname)
                .role(UserRole.USER)
                .device(savedDevice)
                .build();
        Account savedAccount = getSavedAccount(account);

        return savedAccount.getId();

    }

    public Account getAccountByDeviceId(Long deviceId) {
        return accountRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account getSavedAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Device saveAndFindNewDevice(Device device) {
        return deviceRepository.save(device);
    }

}
