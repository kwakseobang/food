package com.kwakmunsu.food.member.service;


import com.kwakmunsu.food.global.exception.FoodBadRequestException;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import com.kwakmunsu.food.member.repositroy.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public void isExistUsername(String username) {
        if (memberRepository.existsByUsername(username))  {
            throw new FoodBadRequestException(FoodErrorCode.DUPLICATE_USERNAME);
        }
    }

    @Transactional(readOnly = true)
    public void isExistNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname))  {
            throw new FoodBadRequestException(FoodErrorCode.DUPLICATE_NICKNAME);
        }
    }
}
