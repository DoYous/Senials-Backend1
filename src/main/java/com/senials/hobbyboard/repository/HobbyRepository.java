package com.senials.hobbyboard.repository;

import com.senials.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer>{
    List<Hobby> findByCategoryNumber(int categoryNumber);

    List<Hobby> findByHobbyAbility(int hobbyAbility);

    List<Hobby> findByHobbyBudget(int hobbyBudget);
    List<Hobby> findByHobbyLevel(int hobbyLevel);
    List<Hobby> findByHobbyTendency(int hobbyTendency);
}
