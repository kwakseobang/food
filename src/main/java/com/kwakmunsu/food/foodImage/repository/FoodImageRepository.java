package com.kwakmunsu.food.foodImage.repository;

import com.kwakmunsu.food.food.domain.FoodType;
import com.kwakmunsu.food.foodImage.domain.FoodImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodImageRepository extends JpaRepository<FoodImage,Long> {
    List<FoodImage> findByFoodRecordId(Long findByFoodRecordId);
}
