package com.eatthis.web.location;

import com.eatthis.api.service.KakaoApiService;
import com.eatthis.web.address.AddressService;
import com.eatthis.web.address.dto.AddressCoordinate;
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

    private final AddressService addressService;
    private final KakaoApiService kakaoApiService;

    public List<LocationSearchDetail> searchStores(StoresByTownSearchDto searchDto) {
        List<KakaoSearchResponseDto> storesByCircle = kakaoApiService.getStoresPagingByCircle(
                searchDto,
                searchDto.getKeyword(),
                searchDto.getCategory(),
                searchDto.getLng(),
                searchDto.getLat(),
                searchDto.getRadius(),
                searchDto.getPage(),
                searchDto.getSize()
        );

        return storesByCircle.stream()
                .map(store ->
                        store.toSearchDetail(
                                kakaoApiService.getImagesByKakaoApi(store.getPlaceName()),
                                kakaoApiService.getBlogsByKakaoApi(store.getPlaceName(), store.getAddress(), 1, 10)
                        )
                )
                .collect(Collectors.toList());
    }

    public List<LocationSearchDetail> searchStoresByTown(StoresByTownSearchDto searchDto) {
        AddressCoordinate addressCoordinate = addressService.getCoordinateByTown(searchDto.getTown());

        List<KakaoSearchResponseDto> storesByCircle = kakaoApiService.getStoresPagingByCircle(
                searchDto,
                searchDto.getKeyword(),
                searchDto.getCategory(),
                addressCoordinate.getLongitude(),
                addressCoordinate.getLatitude(),
                searchDto.getRadius(),
                searchDto.getPage(),
                searchDto.getSize()
        );

        return storesByCircle.stream()
                .map(store ->
                        store.toSearchDetail(
                                kakaoApiService.getImagesByKakaoApi(store.getPlaceName()),
                                kakaoApiService.getBlogsByKakaoApi(store.getPlaceName(), store.getAddress(), 1, 10)
                        )
                )
                .collect(Collectors.toList());
    }
}
