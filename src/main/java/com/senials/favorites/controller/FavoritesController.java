package com.senials.favorites.controller;

import com.senials.favorites.dto.AddFavoriteDTO;
import com.senials.favorites.dto.FavoriteSelectDTO;
import com.senials.favorites.dto.FavoriteTitleDTO;
import com.senials.favorites.service.FavoritesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class FavoritesController {

    private final FavoritesService favoritesService;

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    // 사용자 관심사 제목 가져오기
    @GetMapping("/{userNumber}/favorites")
    public ResponseEntity<List<String>> getUserFavoriteTitles(@PathVariable int userNumber) {
        List<String> favoriteTitles = favoritesService.getFavoriteTitlesByUser(userNumber);
        return ResponseEntity.ok(favoriteTitles);
    }

    //모든 취미, 카테고리명, 저장 여부 가져오기
    @GetMapping("/{userNumber}/favoritesSelect")
    public ResponseEntity<List<FavoriteSelectDTO>> getAllHobbiesWithCategoryAndFavoriteStatus(@PathVariable int userNumber) {
        List<FavoriteSelectDTO> favoriteSelectList = favoritesService.getAllHobbiesWithCategoryAndFavoriteStatus(userNumber);
        return ResponseEntity.ok(favoriteSelectList);
    }

    //관심사 등록
    @PostMapping("/{userNumber}/favorites")
    public ResponseEntity<String> addFavorite(@PathVariable int userNumber, @RequestBody AddFavoriteDTO addFavoriteDto) {
        // 서비스 호출
        favoritesService.addFavorite(userNumber, addFavoriteDto.getHobbyNumber());
        return ResponseEntity.ok("관심사가 추가되었습니다.");
    }

    //관심사 수정
    @PutMapping("/{userNumber}/favorites/{oldHobbyNumber}")
    public ResponseEntity<String> updateFavorite(@PathVariable int userNumber,
                                                 @PathVariable int oldHobbyNumber,
                                                 @RequestBody int newHobbyNumber) {
        try {
            favoritesService.updateFavorite(userNumber, oldHobbyNumber, newHobbyNumber);
            return ResponseEntity.ok("관심사가 수정되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //관심사 삭제
    @DeleteMapping("/{userNumber}/favorites/{hobbyNumber}")
    public ResponseEntity<String> removeFavorite(@PathVariable int userNumber, @PathVariable int hobbyNumber) {
        try {
            favoritesService.removeFavorite(userNumber, hobbyNumber);
            return ResponseEntity.ok("관심사가 삭제되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
