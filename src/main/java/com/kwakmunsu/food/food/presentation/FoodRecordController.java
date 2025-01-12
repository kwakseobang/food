package com.kwakmunsu.food.food.presentation;


import com.kwakmunsu.food.food.dto.request.FoodRecordRequestDto;
import com.kwakmunsu.food.food.dto.response.FoodRecordResponseDto;
import com.kwakmunsu.food.food.dto.response.FoodRecordResponseDto.FoodDetailResponse;
import com.kwakmunsu.food.food.dto.response.FoodRecordResponseDto.FoodListResponse;
import com.kwakmunsu.food.food.service.FoodRecordService;
import com.kwakmunsu.food.global.response.FoodResponseCode;
import com.kwakmunsu.food.global.response.ResponseData;
import com.kwakmunsu.food.global.util.TimeConverter;
import com.kwakmunsu.food.member.dto.request.MemberRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Operation(summary = "음식 기록")
    public ResponseEntity<ResponseData> create(
            @RequestPart(required = false) List<MultipartFile> images,
            @RequestPart(value = "foodRecord") FoodRecordRequestDto.CreateRequest createRequest) {
                foodRecordService.createFoodRecord(images,createRequest);
      return  ResponseData.toResponseEntity(FoodResponseCode.CREATED_FOOD_RECORD);

    }

    @GetMapping
    @Operation(summary = "해당 날짜 음식 기록 리스트 ",description = "사용자가 선택한 날짜의 기록한 음식들을 가져오는 API")
    @Parameters({
            @Parameter(name = "date", description = "선택한 날짜: 형식 - yyyy. M. d.")}
    )
    public ResponseEntity<ResponseData<List<FoodRecordResponseDto.FoodListResponse>>> getRecords(@RequestParam("date") String dateString) {
        LocalDate date = TimeConverter.stringToDate(dateString);
        List<FoodListResponse> foodListResponseList =  foodRecordService.getRecords(date);
        return ResponseData.toResponseEntity(FoodResponseCode.READ_FOOD_RECORDS,foodListResponseList);
    }

    @GetMapping("{foodId}")
    @Operation(summary = "음식 기록 상세 조회", description = "foodId > 0")
    public ResponseEntity<ResponseData<FoodDetailResponse>> getRecord(@PathVariable String foodId) {
        FoodDetailResponse foodDetailResponse = foodRecordService.getRecord(Long.parseLong(foodId));
        return ResponseData.toResponseEntity(FoodResponseCode.READ_FOOD_RECORD,foodDetailResponse);
    }

    @PutMapping
    public ResponseEntity<ResponseData> updateRecord() {
        return ResponseData.toResponseEntity(FoodResponseCode.CREATED_FOOD_RECORD);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteRecord() {
        return ResponseData.toResponseEntity(FoodResponseCode.CREATED_FOOD_RECORD);
    }
}
