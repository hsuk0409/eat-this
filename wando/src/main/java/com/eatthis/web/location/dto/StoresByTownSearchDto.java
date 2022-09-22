package com.eatthis.web.location.dto;

import com.eatthis.handler.exception.CustomException;
import com.eatthis.handler.exception.ErrorCode;
import com.eatthis.web.location.domain.LocationCategoryData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import static com.eatthis.handler.exception.ErrorCode.INVALID_PARAMETER;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoresByTownSearchDto {

    private String keyword;
    private LocationCategoryData category = LocationCategoryData.FOOD;
    private String longitude;
    private String latitude;
    private int radius;
    private String town;
    private int page;
    private int size = 10;

    public void checkValidationParams() {
        if (ObjectUtils.isEmpty(this.town)) {
            if (ObjectUtils.isEmpty(this.longitude) || ObjectUtils.isEmpty(this.latitude)) {
                ErrorCode errorCode = INVALID_PARAMETER;
                errorCode.setCustomDetail("동네 검색어 없을 때 좌표는 필수값입니다.");
                throw new CustomException(errorCode);
            }
        }
    }

    public void initParams() {
        if (ObjectUtils.isEmpty(this.keyword)) {
            this.keyword = this.category.getDescription();
        }
        if (radius == 0) {
            radius = 2000; // default 2km
        }
        if (page <= 0) {
            page = 1;
        }
    }
}
