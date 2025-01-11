package com.kwakmunsu.food.food.repository;


import com.kwakmunsu.food.food.domain.FoodRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRecordRepository extends JpaRepository<FoodRecord,Long> {

}
