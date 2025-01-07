package com.kwakmunsu.food.member.repositroy;

import com.kwakmunsu.food.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

    Optional<Member> findByUsername(String username);
}
