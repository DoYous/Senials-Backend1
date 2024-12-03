package com.senials.favorites.repository;

import com.senials.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {

    // 특정 사용자의 관심사를 조회
    List<Favorites> findByUserNumber(int userNumber);

    // 특정 사용자가 특정 취미를 이미 등록했는지 확인하는 메서드
    boolean existsByUserNumberAndHobby_HobbyNumber(int userNumber, int hobbyNumber);

    // 사용자 번호(userNumber)와 취미 번호(hobbyNumber)로 Favorites 조회
    Favorites findByUserNumberAndHobby_HobbyNumber(int userNumber, int hobbyNumber);

}
