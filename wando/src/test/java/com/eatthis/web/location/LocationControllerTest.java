package com.eatthis.web.location;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("카카오 검색 API 사용하여 삼평동 주위 음식점을 조회한다.")
    @Test
    void kakaoSearchApiTest() throws Exception {
        //given
        String lng = "127.110385";
        String lat = "37.4012017";
        int radius = 100;

        //when & then
        mockMvc.perform(get("/locations/circle")
                        .param("lng", lng)
                        .param("lat", lat)
                        .param("radius", String.valueOf(radius))
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

}