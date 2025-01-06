package com.kwakmunsu.food.auth.service;


import com.kwakmunsu.food.auth.dto.request.AuthRequestDto;
import com.kwakmunsu.food.member.repositroy.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    @Transactional
    public void signup(AuthRequestDto.SignUpRequest signUpRequest) {
        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequest.getPassword());

        memberRepository.save(signUpRequest.toMember(encodedPassword));
    }

}
