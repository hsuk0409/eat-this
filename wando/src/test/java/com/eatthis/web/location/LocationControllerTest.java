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

    @DisplayName("카카오 검색 API 사용하여 특정 위치의 주변 음식점을 조회한다.")
    @Test
    void kakaoSearchApiTest() throws Exception {
        //given
        String lng = "127.04138946025049";
        String lat = "37.51805678063371";
        int radius = 500;

        //when & then
        mockMvc.perform(get("/locations/circle")
                        .param("category", "FOOD")
                        .param("lng", lng)
                        .param("lat", lat)
                        .param("radius", String.valueOf(radius))
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

}