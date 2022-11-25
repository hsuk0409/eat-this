package com.eatthis.domain.accounts;

import com.eatthis.domain.alarms.Alarm;
import com.eatthis.domain.alarms.AlarmRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Transactional
    @DisplayName("계정을 성공적으로 생성한다.")
    @Test
    void createAccountTest() {
        //given
        String nickname = "justin";

        //when
        Account account = getSavedAccount(nickname);

        //then
        assertThat(account).isNotNull();
    }

    @DisplayName("계정을 성공적으로 생성 후 마지막 로그인 시간을 업데이트한다.")
    @Test
    void updateLastLoginDateTimeTest() {
        //given
        String nickname = "justin";
        Account account = getSavedAccount(nickname);

        //when
        LocalDateTime lastLoginDateTimeBefore = account.getLastLoginDateTime();
        updateLastLoginDateTimeOnNewTransaction(account);
        LocalDateTime lastLoginDateTimeAfter = account.getLastLoginDateTime();

        //then
        assertThat(lastLoginDateTimeBefore).isNotEqualTo(lastLoginDateTimeAfter);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateLastLoginDateTimeOnNewTransaction(Account account) {
        account.updateLastLoginDateTime();
    }

    @Transactional
    @DisplayName("계정에 알람을 매핑시킨다.")
    @Test
    void mapAlarmToAccountTest() {
        //given
        String nickname = "justin";

        LocalTime nowTime = LocalTime.now();

        //when
        Account account = getSavedAccount(nickname);

        Alarm alarm = alarmRepository.save(Alarm.builder()
                .build());

        account.addAlarm(alarm);

        //then
        assertThat(account.getAlarms().get(0)).isSameAs(alarm);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account getSavedAccount(String nickname) {
        return accountRepository.save(Account.builder()
                .nickname(nickname)
                .role(UserRole.ADMIN)
                .build());
    }

}