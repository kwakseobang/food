package com.kwakmunsu.food.member.domain;


import com.kwakmunsu.food.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //  외부에서 직접 호출할 수 없고, 같은 패키지 내 또는 상속받은 클래스에서만 접근 가능.
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "social_id", nullable = false, length = 30)
    private String socialId;

    @Column(name = "social_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder // 회원가입 용도
    public Member(
            String email,
            String nickname,
            String imageUrl,
            String socialId,
            SocialType socialType,
            Role role
    ) {

        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.socialId = socialId;
        this.socialType = socialType;
        this.role = role;
    }

    public void updateName(String nickname) {
        this.nickname = nickname;
    }

    public void updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }



}
