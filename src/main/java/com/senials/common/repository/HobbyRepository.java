package com.senials.common.repository;

import com.senials.common.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {
    List<Hobby> findByCategoryNumber(int categoryNumber);

    List<Hobby> findByHobbyAbility(int hobbyAbility);
}
