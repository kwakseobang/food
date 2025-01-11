package com.kwakmunsu.food.food.presentation;


import com.kwakmunsu.food.food.dto.request.FoodRecordRequestDto;
import com.kwakmunsu.food.food.service.FoodRecordService;
import com.kwakmunsu.food.global.response.FoodResponseCode;
import com.kwakmunsu.food.global.response.ResponseData;
import com.kwakmunsu.food.member.dto.request.MemberRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//POST - food
@Tag(name = "Food")
@RestController
@RequiredArgsConstructor
@RequestMapping("/foods")
public class FoodRecordController {

    private final FoodRecordService foodRecordService;

    // POST - 음식 기록, -> dto로 받아옴. 받아와서 저장.
    // PUT - 수정,
    // DELETE - 삭제

    @PostMapping
    public ResponseEntity<ResponseData> create(
            @RequestPart(required = false) List<MultipartFile> images,
            @RequestPart(value = "foodRecord") FoodRecordRequestDto.CreateRequest createRequest) {
                foodRecordService.createFoodRecord(images,createRequest);
      return  ResponseData.toResponseEntity(FoodResponseCode.CREATED_FOOD_RECORD);

    }

}
