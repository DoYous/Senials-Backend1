package com.senials.favorites.service;

import com.senials.entity.Favorites;
import com.senials.entity.Hobby;
import com.senials.favorites.dto.FavoriteSelectDTO;
import com.senials.favorites.repository.FavoritesRepository;
import com.senials.hobby.repository.HobbyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final HobbyRepository hobbyRepository;

    public FavoritesService(FavoritesRepository favoritesRepository, HobbyRepository hobbyRepository) {
        this.favoritesRepository = favoritesRepository;
        this.hobbyRepository = hobbyRepository;
    }


    // 사용자 관심사 제목 가져오기
    public List<String> getFavoriteTitlesByUser(int userNumber) {
        List<Favorites> favorites = favoritesRepository.findByUserNumber(userNumber);
        return favorites.stream()
                .map(favorite -> favorite.getHobby().getHobbyName()) // 취미명만 추출
                .collect(Collectors.toList());
    }

    // 사용자의 관심사 목록 가져오기
    public List<FavoriteSelectDTO> getAllHobbiesWithCategoryAndFavoriteStatus(int userNumber) {
        List<Favorites> userFavorites = favoritesRepository.findByUserNumber(userNumber);
        List<Hobby> allHobbies = hobbyRepository.findAll();

        // 각 취미에 대해 저장 여부와 카테고리명 포함한 DTO 반환
        return allHobbies.stream().map(hobby -> {
            boolean isFavorite = userFavorites.stream()
                    .anyMatch(fav -> fav.getHobby().getHobbyNumber() == hobby.getHobbyNumber());
            return new FavoriteSelectDTO(
                        hobby.getHobbyName(),
                        hobby.getCategory().getCategoryName(),
                        isFavorite
            );
        }).collect(Collectors.toList());
    }
    //관심사 등록
    public void addFavorite(int userNumber, int hobbyNumber) {
        // 사용자가 이미 해당 취미를 즐겨찾기 했는지 확인
        boolean exists = favoritesRepository.existsByUserNumberAndHobby_HobbyNumber(userNumber, hobbyNumber);
        if (exists) {
            throw new RuntimeException("이미 등록된 관심사입니다.");
        }

        // 취미 조회
        Hobby hobby = hobbyRepository.findById(hobbyNumber)
                .orElseThrow(() -> new RuntimeException("취미를 찾을 수 없습니다."));

        // 관심사 추가
        Favorites favorite = new Favorites();
        favorite.setUserNumber(userNumber);
        favorite.setHobby(hobby);

        favoritesRepository.save(favorite);
    }


    //관심사 수정
    public void updateFavorite(int userNumber, int favoriteId, int newHobbyNumber) {
        // 관심사 조회
        Favorites favorite = favoritesRepository.findById(favoriteId)
                .orElseThrow(() -> new RuntimeException("등록된 관심사를 찾을 수 없습니다."));

        // 새로운 취미 번호 조회
        Hobby newHobby = hobbyRepository.findById(newHobbyNumber)
                .orElseThrow(() -> new RuntimeException("새로운 취미를 찾을 수 없습니다."));

        // 관심사 업데이트
        favorite.setHobby(newHobby);

        favoritesRepository.save(favorite);
    }



    //관심사 삭제
    public void removeFavorite(int userNumber, int hobbyNumber) {
        // 관심사 조회
        Favorites favorite = favoritesRepository.findByUserNumberAndHobby_HobbyNumber(userNumber, hobbyNumber);

        // 해당 관심사가 존재하지 않으면 예외 발생
        if (favorite == null) {
            throw new RuntimeException("등록된 관심사를 찾을 수 없습니다.");
        }

        // 관심사 삭제
        favoritesRepository.delete(favorite);
    }



}
