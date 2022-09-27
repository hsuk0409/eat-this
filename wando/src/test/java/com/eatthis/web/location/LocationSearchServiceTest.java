package com.eatthis.web.location;

import com.eatthis.web.location.domain.LocationCategoryData;
import com.eatthis.web.location.dto.LocationSearchDetail;
import com.eatthis.web.location.dto.StoresByTownSearchDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LocationSearchServiceTest {

    @Autowired
    LocationSearchService locationSearchService;

    @DisplayName("동네 위치로 음식점 목록 첫페이지 10개를 조회한다.")
    @Test
    void searchStoresByTown() {
        //given
        StoresByTownSearchDto searchDto = StoresByTownSearchDto.builder()
                .category(LocationCategoryData.FOOD)
                .town("삼성동")
                .build();
        searchDto.initParams();

        //when
        List<LocationSearchDetail> searchDetails = locationSearchService.searchStoresByTown(searchDto);

        //then
        assertThat(searchDetails).isNotEmpty();
        assertThat(searchDetails.size()).isEqualTo(10);
        searchDetails.forEach(info -> assertThat(info.getStoreName()).isNotNull());

    }

}