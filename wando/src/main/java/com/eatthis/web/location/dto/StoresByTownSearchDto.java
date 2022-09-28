package com.eatthis.web.location.dto;

import com.eatthis.handler.exception.CustomException;
import com.eatthis.handler.exception.ErrorCode;
import com.eatthis.web.location.domain.LocationCategoryData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import static com.eatthis.handler.exception.ErrorCode.INVALID_PARAMETER;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoresByTownSearchDto {

    private String keyword;
    private LocationCategoryData category = LocationCategoryData.FOOD;
    private String lng;
    private String lat;
    private int radius;
    private String town;
    private int page;
    private int size = 10;

    public void checkValidationParams() {
        if (ObjectUtils.isEmpty(this.town)) {
            if (ObjectUtils.isEmpty(this.lng) || ObjectUtils.isEmpty(this.lat)) {
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
        if (this.radius == 0) {
            this.radius = 2000; // default 2km
        }
        if (this.page <= 0) {
            this.page = 1;
        }
        if (this.size <= 0) {
            this.size = 10;
        }
    }
}
