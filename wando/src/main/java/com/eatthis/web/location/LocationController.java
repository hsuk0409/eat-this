package com.eatthis.web.location;

import com.eatthis.api.service.KakaoApiService;
import com.eatthis.web.category.CategoryService;
import com.eatthis.web.category.dto.CategoryByStep;
import com.eatthis.web.location.domain.LocationCategoryData;
import com.eatthis.web.location.dto.KakaoSearchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LocationController {

    private final CategoryService categoryService;
    private final KakaoApiService kakaoApiService;

    @GetMapping("/locations/circle")
    public ResponseEntity<List<KakaoSearchResponseDto>> getStores(@RequestParam(value = "keyword", required = false) String keyword,
                                                                  @RequestParam(value = "category", required = false,
                                                                          defaultValue = "FOOD") LocationCategoryData category,
                                                                  @RequestParam(value = "lng") String longitude,
                                                                  @RequestParam(value = "lat") String latitude,
                                                                  @RequestParam(value = "radius") int radius) {
        if (ObjectUtils.isEmpty(keyword)) {
            keyword = category.getDescription();
        }

        List<KakaoSearchResponseDto> responseDtos = kakaoApiService.getStoresByCircle(keyword, category, longitude, latitude, radius);

        return ResponseEntity.ok().body(responseDtos);
    }

    @GetMapping("/locations/circle/category")
    public ResponseEntity<List<CategoryByStep>> getLocationsCategoriesByStep(@RequestParam(value = "keyword", required = false) String keyword,
                                                                             @RequestParam(value = "category", required = false,
                                                                                     defaultValue = "FOOD") LocationCategoryData category,
                                                                             @RequestParam(value = "lng") String longitude,
                                                                             @RequestParam(value = "lat") String latitude,
                                                                             @RequestParam(value = "radius") int radius) {

        if (ObjectUtils.isEmpty(keyword)) {
            keyword = category.getDescription();
        }

        List<KakaoSearchResponseDto> responseDtos = kakaoApiService.getStoresByCircle(keyword, category, longitude, latitude, radius);
        List<CategoryByStep> categoriesByStep = categoryService.subdivideCategoriesByStep(responseDtos);

        return ResponseEntity.ok().body(categoriesByStep);
    }

}
