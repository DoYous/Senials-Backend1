package com.senials.hobbyboard.controller;

import com.senials.common.ResponseMessage;
import com.senials.config.GlobalHttpHeadersConfig;
import com.senials.entity.HobbyReview;
import com.senials.hobbyboard.dto.HobbyReviewDTO;
import com.senials.hobbyboard.service.HobbyReviewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HobbyReviewController {

    int userNumber=1;

    private final HobbyReviewService hobbyReviewService;

    private final GlobalHttpHeadersConfig globalHttpHeadersConfig;

    public HobbyReviewController(HobbyReviewService hobbyReviewService, GlobalHttpHeadersConfig globalHttpHeadersConfig){
        this.hobbyReviewService=hobbyReviewService;
        this.globalHttpHeadersConfig=globalHttpHeadersConfig;
    }


    //취미 후기 작성
    @PostMapping("/{hobbyNumber}/hobby-review")
    public ResponseEntity<ResponseMessage> createHobbyReview(@RequestBody HobbyReviewDTO hobbyReviewDTO, @PathVariable("hobbyNumber")int hobbyNumber) {

        HttpHeaders headers = globalHttpHeadersConfig.createJsonHeaders();

        HobbyReview hobbyReview= hobbyReviewService.saveHobbyReview(hobbyReviewDTO,userNumber,hobbyNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobbyReview",hobbyReview);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(201, "생성 성공", responseMap));
    }

    @DeleteMapping("/{hobbyNumber}/hobby-review/{hobbyReviewNumber}")
    public ResponseEntity<ResponseMessage> deleteHobbyReview(
            @PathVariable("hobbyNumber") int hobbyNumber,
            @PathVariable("hobbyReviewNumber") int hobbyReviewNumber) {
        try {
            hobbyReviewService.deleteHobbyReview(hobbyReviewNumber, userNumber, hobbyNumber);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "삭제 성공");

            return ResponseEntity.ok()
                    .headers(globalHttpHeadersConfig.createJsonHeaders())
                    .body(new ResponseMessage(204, "삭제 성공", responseMap));
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", e.getMessage());

            return ResponseEntity.badRequest()
                    .headers(globalHttpHeadersConfig.createJsonHeaders())
                    .body(new ResponseMessage(400, "삭제 실패", errorMap));
        }

    }
}