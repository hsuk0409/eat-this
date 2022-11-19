package com.eatthis.web.account;

import com.eatthis.domain.accounts.AccountService;
import com.eatthis.handler.exception.CustomException;
import com.eatthis.handler.exception.ErrorCode;
import com.eatthis.utils.AES256;
import com.eatthis.web.account.dto.AccountSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

    @Value("${eat-this-auth-key}")
    private String AUTH_KEY;
    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<Long> saveIfNewAccount(HttpServletRequest request,
                                                 @RequestBody @Valid AccountSaveRequestDto requestDto,
                                                 Errors errors) {

//        checkAuthHeader(request);

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

    private void checkAuthHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        AES256 aes256 = new AES256();
        try {
            String decrypted = aes256.decrypt(authHeader);
            if (!decrypted.equals(AUTH_KEY)) {
                throw new Exception("올바르지 않은 인증키입니다.");
            }
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
            errorCode.setCustomDetail(String.format("인증 정보 확인 중 에러 발생, ex: %s", e.getMessage()));
            throw new CustomException(errorCode);
        }
    }

}
