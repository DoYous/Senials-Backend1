package com.senials.common.repository;

import com.senials.common.entity.Hobby;
import com.senials.common.entity.HobbyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HobbyReviewRepository extends JpaRepository<HobbyReview,Integer> {
    List<HobbyReview> findByHobby(Hobby hobby);
}
