package com.eatthis.web.address;

import com.eatthis.web.address.dto.AddressCoordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressServiceTest {

    @Autowired
    AddressService addressService;

    @DisplayName("카카오 주소 검색하기 API 사용하여 서초동 좌표 정보를 가져온다.")
    @Test
    void testKakaoAddressApi() {
        //given
        String town = "서초동";

        //when
        AddressCoordinate coordinateByTown = addressService.getCoordinateByTown(town);

        //then
        assertThat(coordinateByTown.getLongitude()).isNotNull();
        assertThat(coordinateByTown.getLatitude()).isNotNull();
    }

}