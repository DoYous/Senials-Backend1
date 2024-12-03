package com.senials.likes.repository;

import com.senials.entity.Likes;
import com.senials.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {

    // 특정 사용자가 좋아요한 모든 데이터를 상세하게 조회 (PartyReview와 PartyBoardImage 포함)
    @Query("SELECT l FROM Likes l " +
            "JOIN FETCH l.partyBoard pb " +
            "LEFT JOIN FETCH pb.partyReviews pr " +
            "LEFT JOIN FETCH pb.images img " +
            "WHERE l.user = :user")
    List<Likes> findWithDetailsByUser(@Param("user") User user);

    // 기존 메서드
    List<Likes> findAllByUser(User user);
}

