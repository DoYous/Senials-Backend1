package com.senials.hobbyboard.controller;

import com.senials.common.ResponseMessage;
import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.service.HobbyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class HobbyController {

    private HobbyService hobbyService;

    public HobbyController(HobbyService hobbyService){

        this.hobbyService=hobbyService;
    }

    @GetMapping("/hobby-board")
    public ResponseEntity<ResponseMessage> findAllHobby(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        List<HobbyDTO> hobbyDTOList = hobbyService.findAll();

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobby", hobbyDTOList);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }
}
