package com.kwakmunsu.food.food.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FoodRecordRequestDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private String foodName;
        private Double price;
        private String content;
        private String recodeDate;
        private List<String> foodTypes;
    }




}
