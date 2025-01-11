package com.kwakmunsu.food.food.repository;

import com.kwakmunsu.food.food.domain.FoodType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType,Long> {
    List<FoodType> findByFoodRecordId(Long findByFoodRecordId);
}
