package com.senials.meets.controller;

import com.senials.common.ResponseMessage;
import com.senials.meets.dto.MeetDTO;
import com.senials.meets.service.MeetService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MeetController {

    private final MeetService meetService;


    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }


    /* 모임 내 일정 전체 조회 */
    @GetMapping("/partyboards/{partyBoardNumber}/meets")
    public ResponseEntity<ResponseMessage> getMeetsByPartyBoardNumber(
            @PathVariable Integer partyBoardNumber
    ) {

        List<MeetDTO> meetDTOList = meetService.getMeetsByPartyBoardNumber(partyBoardNumber);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("meets", meetDTOList);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "일정 전체 조회 완료", responseMap));
    }

    /* 모임 일정 추가 */
    @PostMapping("/partyboards/{partyBoardNumber}/meets")
    public ResponseEntity<ResponseMessage> registerMeet(
            @PathVariable Integer partyBoardNumber,
            @RequestBody MeetDTO meetDTO
    ) {
        meetService.registerMeet(partyBoardNumber, meetDTO);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "일정 추가 완료", null));
    }


    /* 모임 일정 수정 */
    /* meetNumber 만으로 고유하기 때문에 partyBoardNumber는 필요없음 */
    @PutMapping("/partyboards/{partyBoardNumber}/meets/{meetNumber}")
    public ResponseEntity<ResponseMessage> modifyMeet (
            @PathVariable Integer meetNumber
            , @RequestBody MeetDTO meetDTO
    ) {
        meetService.modifyMeet(meetNumber, meetDTO);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "일정 수정 완료", null));
    }

    /* 모임 일정 삭제 */
    @DeleteMapping("/partyboards/{partyBoardNumber}/meets/{meetNumber}")
    public ResponseEntity<ResponseMessage> removeMeet (
            @PathVariable Integer meetNumber
    ) {
        meetService.removeMeet(meetNumber);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "일정 삭제 완료", null));
    }
}
