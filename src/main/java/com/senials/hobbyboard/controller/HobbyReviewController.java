package com.senials.hobbyboard.controller;

import com.senials.common.ResponseMessage;
import com.senials.config.GlobalHttpHeadersConfig;
import com.senials.entity.HobbyReview;
import com.senials.hobbyboard.dto.HobbyReviewDTO;
import com.senials.hobbyboard.service.HobbyReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HobbyReviewController {

    private final HobbyReviewService hobbyReviewService;

    private final GlobalHttpHeadersConfig globalHttpHeadersConfig;

    public HobbyReviewController(HobbyReviewService hobbyReviewService, GlobalHttpHeadersConfig globalHttpHeadersConfig){
        this.hobbyReviewService=hobbyReviewService;
        this.globalHttpHeadersConfig=globalHttpHeadersConfig;
    }


    //취미 후기 작성
    @PostMapping("/hobby-review")
    public HobbyReviewDTO createHobbyReview(@RequestBody HobbyReview hobbyReview) {
        return hobbyReviewService.saveHobbyReview(hobbyReview);
    }

    @DeleteMapping("/hobby-review/{reviewNumber}")
    public ResponseEntity<ResponseMessage>


}
