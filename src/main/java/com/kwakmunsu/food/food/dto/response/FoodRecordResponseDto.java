package com.kwakmunsu.food.food.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FoodRecordResponseDto {

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class FoodListResponse {

        private long foodRecordId;
        private String foodName;
        private Double price;
        private String recodeDate;
        private List<FoodTypeResponse> foodTypes;
        private List<FoodImageResponse> foodImages;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class FoodTypeResponse {

        private long foodTypeId;
        private String foodType;
    }


    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class FoodImageResponse {

        private long foodImageId;
        private String imageUrl;
    }

}
