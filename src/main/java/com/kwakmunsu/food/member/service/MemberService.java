package com.kwakmunsu.food.member.service;


import com.kwakmunsu.food.global.exception.FoodBadRequestException;
import com.kwakmunsu.food.global.exception.FoodNotFoundException;
import com.kwakmunsu.food.global.image.service.S3ImageService;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import com.kwakmunsu.food.global.util.JwtUtil;
import com.kwakmunsu.food.member.domain.Member;
import com.kwakmunsu.food.member.repositroy.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final S3ImageService s3ImageService;

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
    @Transactional
    public void updateMember(MultipartFile image, String nickname) {
        // ===========  프로필 변경 ====================
        // 1. 기본 프로필 -> 원하는 사진 - currentImage = x , image = O
        // 2. 기존 사진 -> 기본 프로필  - currentImage = o , image = x
        // 3. 기존 사진 -> 새로운 사진 - currentImage = o , image = O

        Member member = findCurrentMember();
        // 프론트에서 기존 닉네임과 같다면 필터링할것이지만 2차적으로 검사. 기존 닉네임과 같을 경우는 제외하고 검사.
        if (!member.getNickname().equals(nickname)) {
            isExistNickname(nickname);  // 닉네임 중복 검사.
        }
        String currentImage = member.getImageUrl();

        if (currentImage != null) { // 2,3 번
            s3ImageService.deleteImage(currentImage);
        }
        // S3 업로드 경우의 수.
        String newImage = image != null ?  s3ImageService.uploadImage(image) : currentImage;
        // 트랜잭션의 영속성 컨텍스트 특징의 따라 엔티티의 변경 감지 시 DB 자동 Update, 값이 변경되지 않았을 경우 Update x
        member.updateImage(newImage);
        member.updateNickname(nickname);

    }


    @Transactional(readOnly = true)
     public Member findCurrentMember() {
        Long currentMemberId = JwtUtil.getCurrentMemberId();
        Member currentMember = getCurrentUser(currentMemberId);
        return currentMember;
    }
    @Transactional(readOnly = true)
    public Long getCurrentMemberId() {
        return JwtUtil.getCurrentMemberId();
    }



    @Transactional(readOnly = true)
    public Member getCurrentUser(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new FoodNotFoundException(FoodErrorCode.NOT_FOUND_MEMBER));
    }
}
