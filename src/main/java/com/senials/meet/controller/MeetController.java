package com.senials.meet.controller;

import com.senials.mypage.dto.MeetDTO;
import com.senials.meet.service.MeetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userNumber}/meets")
public class MeetController {

    private final MeetService meetService;

    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }

    @GetMapping
    public ResponseEntity<List<MeetDTO>> getUserMeets(@PathVariable int userNumber) {
        List<MeetDTO> meets = meetService.getMeetsByUserNumber(userNumber);
        return ResponseEntity.ok(meets);
    }
}
