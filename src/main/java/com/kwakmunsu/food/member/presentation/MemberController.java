package com.kwakmunsu.food.member.presentation;


import com.kwakmunsu.food.global.response.ResponseCode;
import com.kwakmunsu.food.global.response.ResponseData;
import com.kwakmunsu.food.member.repositroy.MemberRepository;
import com.kwakmunsu.food.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Member")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{nickname}")
    @Operation(summary = "닉네임 중복 확인 [JWT X]")
    public ResponseEntity<ResponseData> isExistNickname(@PathVariable String nickname) {

        return memberService.isExistNickname(nickname)
                ? ResponseData.toResponseEntity(ResponseCode.DUPLICATE_NICKNAME)
                : ResponseData.toResponseEntity(ResponseCode.NICKNAME_SUCCESS);

    }
    @GetMapping("/{username}")
    @Operation(summary = "ID 중복 확인 [JWT X]")
    public ResponseEntity<ResponseData> isExistUsername(@PathVariable String username) {

        return memberService.isExistUsername(username)
                ? ResponseData.toResponseEntity(ResponseCode.DUPLICATE_ID)
                : ResponseData.toResponseEntity(ResponseCode.USERNAME_SUCCESS);

    }
}
