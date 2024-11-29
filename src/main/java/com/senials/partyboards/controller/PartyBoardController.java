package com.senials.partyboards.controller;

import com.senials.common.ResponseMessage;
import com.senials.partyboards.dto.PartyBoardDTO;
import com.senials.partyboards.service.PartyBoardService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class PartyBoardController {

    private PartyBoardService partyBoardService;

    public PartyBoardController(PartyBoardService partyBoardService) {

        this.partyBoardService = partyBoardService;

    }

    // 모임 검색
    @GetMapping("/partyboards/search")
    public ResponseEntity<ResponseMessage> searchPartyBoard(
            @RequestParam(required = false, defaultValue = "lastest") String sortMethod,
            @RequestParam("keyword") String keyword)
    {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        List<PartyBoardDTO> partyBoardDTOList = partyBoardService.searchPartyBoard(keyword);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("partyBoards", partyBoardDTOList);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    // 모임 (전체)조회
    @GetMapping("/partyboards")
    public ResponseEntity<ResponseMessage> findPartyBoards() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return null;
    }
}
