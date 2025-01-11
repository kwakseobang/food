package com.kwakmunsu.food.member.presentation;


import com.kwakmunsu.food.global.response.FoodResponseCode;
import com.kwakmunsu.food.global.response.ResponseData;
import com.kwakmunsu.food.member.dto.request.MemberRequestDto;
import com.kwakmunsu.food.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "Member")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/nickname/{nickname}")
    @Operation(summary = "닉네임 중복 확인 ")
    public ResponseEntity<ResponseData> isExistNickname(@PathVariable String nickname) {
        memberService.isExistNickname(nickname);
        return ResponseData.toResponseEntity(FoodResponseCode.NICKNAME_SUCCESS);

    }
    @GetMapping("/username/{username}")
    @Operation(summary = "ID 중복 확인 ")
    public ResponseEntity<ResponseData> isExistUsername(@PathVariable String username) {
        memberService.isExistUsername(username);
        return ResponseData.toResponseEntity(FoodResponseCode.USERNAME_SUCCESS);

    }

    @PutMapping()
    @Operation(summary = "회원 정보 변경")
    public ResponseEntity<ResponseData> update(
            @RequestPart(required = false) MultipartFile image,
            @RequestPart(value = "updateInfo") MemberRequestDto.UpdateInfoRequest updateRequest
    ) {
        memberService.updateMember(image, updateRequest.getNickname());
        return ResponseData.toResponseEntity(FoodResponseCode.UPDATE_MEMBER);
    }
}
