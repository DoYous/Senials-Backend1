package com.senials.hobbyboard.controller;

import com.senials.common.ResponseMessage;
import com.senials.config.GlobalHttpHeadersConfig;
import com.senials.common.entity.Favorites;
import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.service.SuggestHobbyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SuggestHobbyController {

    int userNumber=1;

    private final GlobalHttpHeadersConfig globalHttpHeadersConfig;
    private final SuggestHobbyService suggestHobbyService;


    public SuggestHobbyController(SuggestHobbyService suggestHobbyService, GlobalHttpHeadersConfig globalHttpHeadersConfig){
        this.suggestHobbyService=suggestHobbyService;
        this.globalHttpHeadersConfig=globalHttpHeadersConfig;

    }

    //맞춤형 취미 추천 결과 조회
    @GetMapping("/suggest-hobby-result")
    public ResponseEntity<ResponseMessage> getSuggestHobby(@RequestParam int hobbyAbility,
                                                           @RequestParam int hobbyBudget,
                                                           @RequestParam int hobbyLevel,
                                                           @RequestParam int hobbyTendency) {

        HttpHeaders headers = globalHttpHeadersConfig.createJsonHeaders();

        HobbyDTO hobbyDTO = suggestHobbyService.suggestHobby(hobbyAbility, hobbyBudget,hobbyTendency, hobbyLevel);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobby", hobbyDTO);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(201, "생성 성공", responseMap));
    }

    //맞춤형 취미 추천 나의 취미 관심사 등록
    @PostMapping("/suggest-hobby-result")
    public ResponseEntity<ResponseMessage> setSuggestHobby(@RequestParam int hobbyNumber){

        HttpHeaders headers = globalHttpHeadersConfig.createJsonHeaders();

        Favorites favorites=suggestHobbyService.setFavoritesByHobby(hobbyNumber,userNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("favorites",favorites);
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(201, "생성 성공", responseMap));

    }
}
