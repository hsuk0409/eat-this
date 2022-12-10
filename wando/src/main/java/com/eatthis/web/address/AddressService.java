package com.eatthis.web.address;

import com.eatthis.api.kakao.KakaoAddressService;
import com.eatthis.handler.exception.CustomException;
import com.eatthis.handler.exception.ErrorCode;
import com.eatthis.web.address.dto.AddressCoordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.eatthis.handler.exception.ErrorCode.INVALID_PARAMETER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final KakaoAddressService kakaoAddressService;

    public AddressCoordinate getCoordinateByTown(String town) {
        List<Map<String, Object>> documents = kakaoAddressService.getCoordinateByTown(town);
        if (documents.isEmpty()) {
            ErrorCode errorCode = INVALID_PARAMETER;
            errorCode.setCustomDetail(String.format("[%s]에 대한 좌표 정보가 조회되지 않습니다. 다시 확인해주세요.", town));
            throw new CustomException(errorCode);
        }

        Map<String, Object> addressMap = documents.get(0);
        return AddressCoordinate.builder()
                .addressName(String.valueOf(addressMap.get("address_name")))
                .longitude(String.valueOf(addressMap.get("x")))
                .latitude(String.valueOf(addressMap.get("y")))
                .build();
    }
}
