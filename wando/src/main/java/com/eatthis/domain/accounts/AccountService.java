package com.eatthis.domain.accounts;

import com.eatthis.utils.UUIDGenerateUtils;
import com.eatthis.web.account.dto.AccountSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Long saveIfNewAccount(AccountSaveRequestDto requestDto) {
        String fcmToken = requestDto.getFcmToken();
        Optional<Account> accountOptional = accountRepository
                .findByFcmToken(fcmToken);
        if (accountOptional.isPresent()) return accountOptional.get().getId();

        String requestNickName = requestDto.getNickname();
        String nickname = ObjectUtils.isEmpty(requestNickName) ?
                UUIDGenerateUtils.makeShorUUID(6) : requestNickName;

        return accountRepository.save(Account.builder()
                .nickname(nickname)
                .fcmToken(fcmToken)
                .role(UserRole.USER)
                .build()).getId();

    }
}
