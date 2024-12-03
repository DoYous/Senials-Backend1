package com.senials.likes.controller;

import com.senials.likes.service.LikesService;
import com.senials.partyboards.dto.PartyBoardDTOForCard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class LikesController {

    private final LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    //사용자가 좋아한 모임 목록
    @GetMapping("/{userNumber}/likes")
    public ResponseEntity<List<PartyBoardDTOForCard>> getLikedPartyBoards(@PathVariable int userNumber) {
        List<PartyBoardDTOForCard> likedBoards = likesService.getLikedPartyBoardsByUserNumber(userNumber);
        return ResponseEntity.ok(likedBoards);
    }

}
