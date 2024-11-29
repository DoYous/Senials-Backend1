package com.senials.hobbyboard.repository;

import com.senials.entity.Hobby;
import com.senials.entity.HobbyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HobbyReviewRepository extends JpaRepository<HobbyReview,Integer> {
    List<HobbyReview> findByHobby(Hobby hobby);
}
