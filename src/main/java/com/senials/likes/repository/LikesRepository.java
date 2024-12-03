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

    // 특정 사용자가 좋아요한 모든 데이터를 조회 (이미지와 리뷰 제거)
    @Query("SELECT l FROM Likes l " +
            "JOIN FETCH l.partyBoard pb " +
            "WHERE l.user = :user")
    List<Likes> findWithDetailsByUser(@Param("user") User user);

    List<Likes> findAllByUser(User user);
}
