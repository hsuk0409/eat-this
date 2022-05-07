package com.eatthis.eatthis.web.location;

import com.eatthis.eatthis.web.api.service.KakaoApiService;
import com.eatthis.eatthis.web.location.domain.LocationCategory;
import com.eatthis.eatthis.web.location.dto.KakaoSearchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final KakaoApiService kakaoApiService;

    @GetMapping("/circle")
    public ResponseEntity<KakaoSearchResponseDto> getStores(@RequestParam(value = "keyword", required = false) String keyword,
                                                            @RequestParam(value = "category", required = false) LocationCategory category,
                                                            @RequestParam(value = "lng") String longitude,
                                                            @RequestParam(value = "lat") String latitude,
                                                            @RequestParam(value = "radius") int radius) {
        if (ObjectUtils.isEmpty(keyword)) {
            keyword = LocationCategory.FOOD.getDescription();
        }
        if (ObjectUtils.isEmpty(category)) {
            category = LocationCategory.FOOD;
        }

        KakaoSearchResponseDto responseDto = kakaoApiService.getStoresByCircle(keyword, category, longitude, latitude, radius);

        return ResponseEntity.ok().body(responseDto);
    }

}
