package com.eatthis.web.location;

import com.eatthis.api.service.KakaoApiService;
import com.eatthis.web.location.dto.KakaoSearchResponseDto;
import com.eatthis.web.location.dto.LocationSearchDetail;
import com.eatthis.web.location.dto.StoresByTownSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationSearchService {

    private final KakaoApiService kakaoApiService;

    public List<LocationSearchDetail> searchStores(StoresByTownSearchDto searchDto) {
        List<KakaoSearchResponseDto> storesByCircle = kakaoApiService.getStoresByCircle(
                searchDto.getKeyword(),
                searchDto.getCategory(),
                searchDto.getLongitude(),
                searchDto.getLatitude(),
                searchDto.getRadius()
        );

        return storesByCircle.stream()
                .map(store -> store.toSearchDetail(
                        kakaoApiService.getImagesByKakaoApi(store.getPlaceName()))
                )
                .collect(Collectors.toList());
    }

    public List<LocationSearchDetail> searchStoresByTown(StoresByTownSearchDto searchDto) {

        return null;
    }
}
