package com.kwakmunsu.food.auth.jwt.repository;


import com.kwakmunsu.food.auth.jwt.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {

}
