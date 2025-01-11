package com.kwakmunsu.food.foodImage.domain;


import com.kwakmunsu.food.food.domain.FoodRecord;
import com.kwakmunsu.food.food.type.FoodTypeEnum;
import com.kwakmunsu.food.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name ="food_image")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_record_id", nullable = false) // 외래 키
    private FoodRecord foodRecord;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;


    @Builder(builderClassName = "FoodImageBuilder", builderMethodName = "FoodImageBuilder")
    public FoodImage(
            FoodRecord foodRecord,
            String imageUrl
    ) {
        this.foodRecord = foodRecord;
        this.imageUrl = imageUrl;
    }

}
