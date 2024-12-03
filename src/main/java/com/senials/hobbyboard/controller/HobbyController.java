package com.senials.hobbyboard.controller;

import com.senials.common.ResponseMessage;
import com.senials.config.GlobalHttpHeadersConfig;
import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.dto.HobbyReviewDTO;
import com.senials.hobbyboard.service.HobbyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class HobbyController {

    private HobbyService hobbyService;
    private final GlobalHttpHeadersConfig globalHttpHeadersConfig;

    public HobbyController(HobbyService hobbyService, GlobalHttpHeadersConfig globalHttpHeadersConfig){

        this.hobbyService=hobbyService;
        this.globalHttpHeadersConfig = globalHttpHeadersConfig;
    }

    //취미 전체 조회
    @GetMapping("/hobby-board")
    public ResponseEntity<ResponseMessage> findHobbyAll(){

        HttpHeaders headers = globalHttpHeadersConfig.createJsonHeaders();

        List<HobbyDTO> hobbyDTOList = hobbyService.findAll();

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobby", hobbyDTOList);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    //취미 상세 조회
    @GetMapping("/hobby-detail/{hobbyNumber}")
    public ResponseEntity<ResponseMessage> findHobbyDetail(@PathVariable("hobbyNumber")int hobbyNumber){

        HttpHeaders headers = globalHttpHeadersConfig.createJsonHeaders();

        HobbyDTO hobbyDTO = hobbyService.findById(hobbyNumber);
        List<HobbyReviewDTO> hobbyReviewDTOList=hobbyService.getReviewsListByHobbyNumber(hobbyNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobbyReview",hobbyReviewDTOList);
        responseMap.put("hobby", hobbyDTO);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    //취미 카테고리별 취미 목록 조회
    @GetMapping("/hobby-board/{categoryNumber}")
    public ResponseEntity<ResponseMessage> findHobbyByCategory(@PathVariable("categoryNumber")int categoryNumber){

        HttpHeaders headers = globalHttpHeadersConfig.createJsonHeaders();

        List<HobbyDTO> hobbyDTOList = hobbyService.findByCategory(categoryNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobby", hobbyDTOList);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

}
