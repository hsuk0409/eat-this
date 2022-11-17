package com.eatthis.web.account;

import com.eatthis.domain.accounts.AccountService;
import com.eatthis.handler.exception.CustomException;
import com.eatthis.handler.exception.ErrorCode;
import com.eatthis.web.account.dto.AccountSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<Long> saveIfNewAccount(@RequestBody @Valid AccountSaveRequestDto requestDto,
                                                 Errors errors) {

        if (errors.hasErrors()) {
            ErrorCode errorCode = ErrorCode.PARAMETER_IS_REQUIRED;
            String message = errors.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(","));
            errorCode.setCustomDetail(message);
            throw new CustomException(errorCode);
        }

        return ResponseEntity.ok()
                .body(accountService.saveIfNewAccount(requestDto));

    }

}
