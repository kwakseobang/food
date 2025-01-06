package com.kwakmunsu.food.member.service;


import com.kwakmunsu.food.member.repositroy.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public boolean isExistUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean isExistNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
