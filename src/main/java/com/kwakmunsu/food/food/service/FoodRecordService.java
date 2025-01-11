package com.kwakmunsu.food.food.service;

import com.kwakmunsu.food.auth.jwt.JwtProvider;
import com.kwakmunsu.food.food.domain.FoodRecord;
import com.kwakmunsu.food.food.domain.FoodType;
import com.kwakmunsu.food.food.dto.request.FoodRecordRequestDto;
import com.kwakmunsu.food.food.repository.FoodRecordRepository;
import com.kwakmunsu.food.food.repository.FoodTypeRepository;
import com.kwakmunsu.food.food.type.FoodTypeEnum;
import com.kwakmunsu.food.foodImage.domain.FoodImage;
import com.kwakmunsu.food.foodImage.repository.FoodImageRepository;
import com.kwakmunsu.food.global.image.service.S3ImageService;
import com.kwakmunsu.food.global.util.TimeConverter;
import com.kwakmunsu.food.member.domain.Member;
import com.kwakmunsu.food.member.service.MemberService;
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

        for (String type: createRequest.getFoodTypes()) { // 기록된 음식의 카테고리 저장
            // 한글을 FoodTypeEnum으로 변환
            FoodTypeEnum foodTypeEnum = FoodTypeEnum.fromString(type);
            FoodType foodType = saveFoodType(foodTypeEnum, foodRecord);  // 새로운 FoodType 객체 생성
            foodTypeRepository.save(foodType);  // FoodType 저장
        }
        List<String> images = s3ImageService.uploadImages(files);

        for(String image: images) { // 기록된 음식 사진 저장
            FoodImage foodImage = saveFoodImage(foodRecord,image);
            foodImageRepository.save(foodImage);
        }
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
                .recode_date(TimeConverter.stringToTime(createRequest.getRecodeDate()))
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
}
