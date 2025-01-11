package com.kwakmunsu.food.food.repository;


import com.kwakmunsu.food.food.domain.FoodRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRecordRepository extends JpaRepository<FoodRecord,Long> {
    // 해당 날짜에 속하는 음식 기록들 다 가져오게 범위 설정.
    //JPA에서는 member.id로 접근하는 이유는 FoodRecord 엔티티의 member 필드가 Member 객체이기 때문이다.
    // 즉, FoodRecord에서 member는 Member 객체로 매핑되고, 이 객체에서 id 값을 가져오려면 member.id로 접근해야 함.
    @Query("SELECT f FROM FoodRecord f WHERE f.recode_date BETWEEN :startDate AND :endDate AND f.member.id = :memberId")
    List<FoodRecord> findByRecodeDate(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("memberId") Long memberId
    );

}
