package com.kwakmunsu.food.food.domain;


import com.kwakmunsu.food.food.type.FoodTypeEnum;
import com.kwakmunsu.food.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Table(name ="food_type")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private FoodTypeEnum typeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_record_id", nullable = false) // 외래 키
    private FoodRecord foodRecord;


    @Builder(builderClassName = "FoodTypeBuilder", builderMethodName = "FoodTypeBuilder")
    public FoodType(
            FoodTypeEnum typeName,
            FoodRecord foodRecord
    ) {
        this.typeName = typeName;
        this.foodRecord = foodRecord;
    }
}
