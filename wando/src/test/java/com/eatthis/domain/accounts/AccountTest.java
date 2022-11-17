package com.eatthis.domain.accounts;

import com.eatthis.domain.alarms.Alarm;
import com.eatthis.domain.alarms.AlarmRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional(readOnly = true)
class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Transactional
    @DisplayName("H2 DB 사용하여 계정을 성공적으로 생성한다.")
    @Test
    void createAccountTest() {
        //given
        String nickname = "justin";
        String fcmToken = "justinToken";

        //when
        Account account = accountRepository.save(Account.builder()
                .nickname(nickname)
                .fcmToken(fcmToken)
                .role(UserRole.ADMIN)
                .build());

        //then
        assertThat(account).isNotNull();
    }

    @Transactional
    @DisplayName("H2 DB 사용하여 계정에 알람을 매핑시킨다.")
    @Test
    void mapAlarmToAccountTest() {
        //given
        String nickname = "justin";
        String fcmToken = "justinToken";

        LocalTime nowTime = LocalTime.now();

        //when
        Account account = accountRepository.save(Account.builder()
                .nickname(nickname)
                .fcmToken(fcmToken)
                .role(UserRole.ADMIN)
                .build());

        Alarm alarm = alarmRepository.save(Alarm.builder()
                .startTime(nowTime)
                .build());
        account.addAlarm(alarm);

        //then
        assertThat(account.getAlarms().get(0)).isSameAs(alarm);
    }

}