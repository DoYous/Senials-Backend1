package com.senials.partymembers.controller;

import com.senials.common.ResponseMessage;
import com.senials.user.dto.UserDTOForPublic;
import com.senials.partymembers.service.PartyMemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PartyMemberController {

    private final PartyMemberService partyMemberService;


    public PartyMemberController(
            PartyMemberService partyMemberService
    ) {
        this.partyMemberService = partyMemberService;
    }

    /* 모임 멤버 전체 조회 */
    @GetMapping("/partyboards/{partyBoardNumber}/partymembers")
    public ResponseEntity<ResponseMessage> getPartyMembers (
            @PathVariable Integer partyBoardNumber
    ) {
        List<UserDTOForPublic> userDTOForPublicList = partyMemberService.getPartyMembers(partyBoardNumber);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("partyMembers", userDTOForPublicList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "모임 멤버 전체 조회 성공", responseMap));
    }

    /* 모임 참가 */
    @PostMapping("/partyboards/{partyBoardNumber}/partymembers")
    public ResponseEntity<ResponseMessage> registerPartyMember (
            @PathVariable Integer partyBoardNumber
    ) {
        // 유저 번호 임의 지정
        int userNumber = 2;

        partyMemberService.registerPartyMember(userNumber, partyBoardNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "모임 가입 성공", null));
    }

    /* 모임 탈퇴 */
    @DeleteMapping("/partyboards/{partyBoardNumber}/partymembers/{partyMemberNumber}")
    public ResponseEntity<ResponseMessage> unregisterPartyMember (
            @PathVariable Integer partyMemberNumber
    ) {

        partyMemberService.unregisterPartyMember(partyMemberNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "모임 탈퇴 성공", null));
    }

}
