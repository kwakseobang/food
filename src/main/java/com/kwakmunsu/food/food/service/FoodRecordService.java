package com.kwakmunsu.food.food.service;

import com.kwakmunsu.food.food.domain.FoodRecord;
import com.kwakmunsu.food.food.domain.FoodType;
import com.kwakmunsu.food.food.dto.request.FoodRecordRequestDto;
import com.kwakmunsu.food.food.dto.response.FoodRecordResponseDto;
import com.kwakmunsu.food.food.dto.response.FoodRecordResponseDto.FoodDetailResponse;
import com.kwakmunsu.food.food.dto.response.FoodRecordResponseDto.FoodImageResponse;
import com.kwakmunsu.food.food.dto.response.FoodRecordResponseDto.FoodListResponse;
import com.kwakmunsu.food.food.dto.response.FoodRecordResponseDto.FoodTypeResponse;
import com.kwakmunsu.food.food.repository.FoodRecordRepository;
import com.kwakmunsu.food.food.repository.FoodTypeRepository;
import com.kwakmunsu.food.food.type.FoodTypeEnum;
import com.kwakmunsu.food.foodImage.domain.FoodImage;
import com.kwakmunsu.food.foodImage.repository.FoodImageRepository;
import com.kwakmunsu.food.global.exception.FoodBadRequestException;
import com.kwakmunsu.food.global.exception.FoodException;
import com.kwakmunsu.food.global.exception.FoodNotFoundException;
import com.kwakmunsu.food.global.image.service.S3ImageService;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import com.kwakmunsu.food.global.util.TimeConverter;
import com.kwakmunsu.food.member.domain.Member;
import com.kwakmunsu.food.member.service.MemberService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FoodRecordService {

    private final MemberService memberService;
    private final FoodRecordRepository foodRecordRepository;
    private final FoodTypeRepository foodTypeRepository;
    private final FoodImageRepository foodImageRepository;
    private final S3ImageService s3ImageService;

    @Transactional
    public void createFoodRecord(
            List<MultipartFile> files,
            FoodRecordRequestDto.CreateRequest createRequest
    ) {
        Member currentMember = memberService.findCurrentMember();
        FoodRecord foodRecord = saveFoodRecord(currentMember, createRequest);
        foodRecord = foodRecordRepository.save(foodRecord); // 음식 기록 저장

        for (String type : createRequest.getFoodTypes()) { // 기록된 음식의 카테고리 저장
            // 한글을 FoodTypeEnum으로 변환
            FoodTypeEnum foodTypeEnum = FoodTypeEnum.fromString(type);
            FoodType foodType = saveFoodType(foodTypeEnum, foodRecord);  // 새로운 FoodType 객체 생성
            foodTypeRepository.save(foodType);  // FoodType 저장
        }
        if( files != null) {
            List<String> images = s3ImageService.uploadImages(files);

            for (String image : images) { // 기록된 음식 사진 저장
                FoodImage foodImage = saveFoodImage(foodRecord, image);
                foodImageRepository.save(foodImage);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<FoodListResponse> getRecords(LocalDate localDate) {
        Long currentMemberId = memberService.getCurrentMemberId();
        // 해당 날짜에 해당하는 음식 기록들 가져오기 위해 범위 설정.
        LocalDateTime startDateTime = localDate.atStartOfDay(); // 2025-01-11 00:00:00
        LocalDateTime endDateTime = localDate.atTime(23, 59, 59, 999999999); // 2025-01-11 23:59:59
        List<FoodRecord> foodRecordList = foodRecordRepository.findByRecodeDate(
                startDateTime,
                endDateTime,
                currentMemberId
        );
        if (foodRecordList.isEmpty()) {
            throw new FoodBadRequestException(FoodErrorCode.NOT_FOUND_FOOD_RECORDS);
        }
        List<FoodRecordResponseDto.FoodListResponse> foodListResponseList = new ArrayList<>();
        for (FoodRecord foodRecord : foodRecordList) {
            Long foodRecordId = foodRecord.getId();

            List<FoodTypeResponse> foodTypeResponseList = getFoodTypeResponseList(foodRecordId);
            List<FoodImageResponse> foodImageResponseList = getFoodImageResponseList(foodRecordId);

            FoodListResponse foodListResponse = createFoodListResponse(
                    foodRecord,
                    foodTypeResponseList,
                    foodImageResponseList
            );
            foodListResponseList.add(foodListResponse);
        }
        return foodListResponseList;
    }

    @Transactional(readOnly = true)
    public  FoodDetailResponse getRecord(Long foodRecordId) {
        // 아이디로 상세 정보를 가져와야ㄴ
       FoodRecord foodRecord =  foodRecordRepository.findById(foodRecordId)
                 .orElseThrow(() -> new FoodNotFoundException(FoodErrorCode.NOT_FOUND_FOOD_RECORD));

        List<FoodTypeResponse> foodTypeResponseList = getFoodTypeResponseList(foodRecordId);
        List<FoodImageResponse> foodImageResponseList = getFoodImageResponseList(foodRecordId);

       FoodDetailResponse foodDetailResponse = createFoodDetailResponse(
               foodRecord,
               foodTypeResponseList,
               foodImageResponseList
       );
       return foodDetailResponse;
    }



    private FoodRecord saveFoodRecord(
            Member currentMember,
            FoodRecordRequestDto.CreateRequest createRequest
    ) {
        return FoodRecord.FoodRecordBuilder()
                .member(currentMember)
                .foodName(createRequest.getFoodName())
                .content(createRequest.getContent())
                .price(createRequest.getPrice())
                .recode_date(TimeConverter.stringToDateTime(createRequest.getRecodeDate()))
                .build();
    }

    private FoodType saveFoodType(FoodTypeEnum foodType, FoodRecord foodRecord) {
        return FoodType.FoodTypeBuilder()
                .typeName(foodType)
                .foodRecord(foodRecord)
                .build();
    }

    private FoodImage saveFoodImage(FoodRecord foodRecord, String imageUrl) {
        return FoodImage.FoodImageBuilder()
                .foodRecord(foodRecord)
                .imageUrl(imageUrl)
                .build();
    }

    private List<FoodTypeResponse> getFoodTypeResponseList(Long foodRecordId) {
        try {
            List<FoodType> foodTypeList = foodTypeRepository.findByFoodRecordId(foodRecordId);
            return convertToFoodTypeResponse(foodTypeList);
        } catch (Exception e) {
            throw new FoodNotFoundException(FoodErrorCode.NOT_FOUND_FOOD_RECORD, e.getMessage());
        }
    }

    private List<FoodImageResponse> getFoodImageResponseList(Long foodRecordId) {
        try {
            List<FoodImage> foodImageList = foodImageRepository.findByFoodRecordId(foodRecordId);
            return convertToFoodImageResponse(foodImageList);
        } catch (Exception e) {
            throw new FoodNotFoundException(FoodErrorCode.NOT_FOUND_FOOD_RECORD, e.getMessage());
        }
    }

    // 날짜에 해당하는 음식 카테고리 응답 객체 생성
    private List<FoodTypeResponse> convertToFoodTypeResponse(List<FoodType> foodTypeList) {
        List<FoodTypeResponse> foodTypeResponseList = new ArrayList<>();
        for (FoodType foodType : foodTypeList) {
            FoodTypeResponse foodTypeResponse = FoodTypeResponse.builder()
                    .foodTypeId(foodType.getId())
                    .foodType(foodType.getTypeName().getFoodType())
                    .build();
            foodTypeResponseList.add(foodTypeResponse);
        }
        return foodTypeResponseList;
    }


    // 날짜에 해당하는 음식 기록 사진 응답 객체 생성
    private List<FoodImageResponse> convertToFoodImageResponse(List<FoodImage> foodImageList) {
        List<FoodImageResponse> foodImageResponseList = new ArrayList<>();
        for (FoodImage foodImage : foodImageList) {
            FoodImageResponse foodImageResponse = FoodImageResponse.builder()
                    .foodImageId(foodImage.getId())
                    .imageUrl(foodImage.getImageUrl())
                    .build();
            foodImageResponseList.add(foodImageResponse);
        }
        return foodImageResponseList;
    }

    // 날짜에 해당하는 음식 기록 응답 객체 생성
    private FoodListResponse createFoodListResponse(
            FoodRecord foodRecord,
            List<FoodTypeResponse> foodTypeResponseList,
            List<FoodImageResponse> foodImageResponseList
    ) {
        return FoodListResponse.builder()
                .foodRecordId(foodRecord.getId())
                .foodName(foodRecord.getFoodName())
                .price(foodRecord.getPrice())
                .recodeDate(TimeConverter.DatetimeToString(foodRecord.getRecode_date()))
                .foodTypes(foodTypeResponseList)
                .foodImages(foodImageResponseList)
                .build();
    }

    private FoodDetailResponse createFoodDetailResponse(
            FoodRecord foodRecord,
            List<FoodTypeResponse> foodTypeResponseList,
            List<FoodImageResponse> foodImageResponseList
    ) {
        return  FoodDetailResponse.builder()
                .foodRecordId(foodRecord.getId())
                .memberId(foodRecord.getMember().getId())
                .foodName(foodRecord.getFoodName())
                .price(foodRecord.getPrice())
                .content(foodRecord.getContent())
                .recodeDate(TimeConverter.DatetimeToString(foodRecord.getRecode_date()))
                .foodTypes(foodTypeResponseList)
                .foodImages(foodImageResponseList)
                .build();
    }
}
