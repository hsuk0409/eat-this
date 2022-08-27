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
    void kakaoSearchApiByCircleTest() throws Exception {
        //given
        String lat = "35.79428711697479";
        String lng = "127.15892066580103";
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

    @DisplayName("카카오 검색 API 사용하여 사각형 위치의 주변 음식점을 조회한다.")
    @Test
    void kakaoSearchApiByRectTest() throws Exception {
        //given
        String rect = "127.112597264,37.398321708,127.108383849,37.404448936";

        //when & then
        mockMvc.perform(get("/locations/rectangle")
                        .param("category", "FOOD")
                        .param("rectCoordinate", rect)
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("사각형 위치의 주변 카테고리화 된 음식점을 조회한다.")
    @Test
    void getStoresByCategoryStepByRectTest() throws Exception {
        //given
        String rect = "127.112597264,37.398321708,127.108383849,37.404448936";

        //when & then
        mockMvc.perform(get("/locations/rectangle/category")
                        .param("category", "FOOD")
                        .param("rectCoordinate", rect)
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

}