package com.kwakmunsu.food.food.domain;

import com.kwakmunsu.food.global.entity.BaseEntity;
import com.kwakmunsu.food.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name ="food_record")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //  외부에서 직접 호출할 수 없고, 같은 패키지 내 또는 상속받은 클래스에서만 접근 가능.
public class FoodRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(name = "price")
    private Double price;

    @Column(name = "content")
    private String content;

    @Column(name = "recode_date", nullable = false)
    private LocalDateTime recode_date;


    @Builder(builderClassName = "FoodRecordBuilder", builderMethodName = "FoodRecordBuilder")
    public FoodRecord(
            Member member,
            String foodName,
            Double price,
            String content,
            LocalDateTime recode_date
    ) {
        this.member = member;
        this.foodName = foodName;
        this.price = price;
        this.content = content;
        this.recode_date = recode_date;
    }




}
