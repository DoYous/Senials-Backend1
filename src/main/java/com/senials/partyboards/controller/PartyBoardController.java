package com.senials.partyboards.controller;

import com.senials.common.ResponseMessage;
import com.senials.partyboards.dto.PartyBoardDTO;
import com.senials.partyboards.dto.PartyBoardDTOForDetail;
import com.senials.partyboards.dto.PartyBoardDTOForModify;
import com.senials.partyboards.dto.PartyBoardDTOForWrite;
import com.senials.partyboards.repository.PartyBoardRepository;
import com.senials.partyboards.service.PartyBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class PartyBoardController {

    private final ResourceLoader resourceLoader;
    private final PartyBoardService partyBoardService;

    @Autowired
    public PartyBoardController(ResourceLoader resourceLoader, PartyBoardService partyBoardService) {
        this.resourceLoader = resourceLoader;
        this.partyBoardService = partyBoardService;
    }

    // 모임 검색
    // 쿼리스트링
    // 1. keyword : 검색어
    // 2. sortMethod : 검색결과 정렬기준 (기본 - 최신순; lastest)
    // 3. cursor : 클라이언트가 마지막으로 받은 검색결과 partyBoardNumber (마지막 검색결과 다음 행부터 N개 게시글 Response)
    // @GetMapping("/partyboards/search")
    // public ResponseEntity<ResponseMessage> searchPartyBoard(
    //     @RequestParam(required = false) String keyword,
    //     @RequestParam(required = false, defaultValue = "lastest") String sortMethod,
    //     @RequestParam(required = false) boolean isAscending,
    //     @RequestParam(required = false) Integer cursor
    // )
    // {
    //     String sortColumn = null;
    //
    //     switch (sortMethod) {
    //         // 최신순, 오래된순
    //         case "lastest":
    //             sortColumn = "party_board_open_date";
    //             break;
    //         case "oldest":
    //             sortColumn = "party_board_open_date";
    //             break;
    //         // 좋아요순
    //         case "mostLiked":
    //             sortColumn = "party_board_like_cnt";
    //             break;
    //         // 조회순
    //         case "mostViewed":
    //             sortColumn = "party_board_view_cnt";
    //             break;
    //         default:
    //     }
    //
    //     String compareChar = "<";
    //     String sortDirection = "desc";
    //     if (isAscending) {
    //         compareChar = ">";
    //         sortDirection = "asc";
    //     }
    //
    //     List<PartyBoardDTO> partyBoardDTOList = partyBoardService.searchPartySorting(keyword, sortColumn, compareChar, sortDirection, cursor);
    //
    //     // ResponseHeader 설정
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
    //     // ResponseBody 삽입
    //     Map<String, Object> responseMap = new HashMap<>();
    //     responseMap.put("partyBoards", partyBoardDTOList);
    //
    //     return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    // }

    // 모임 상세 조회
    @GetMapping("/partyboards/{partyBoardNumber}")
    public ResponseEntity<ResponseMessage> getPartyBoardByNumber(@PathVariable Integer partyBoardNumber) {

        PartyBoardDTOForDetail partyBoardDTO = partyBoardService.getPartyBoardByNumber(partyBoardNumber);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        // ResponseBody 삽입
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("partyBoard", partyBoardDTO);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    /* 모임 글 작성 */
    @PostMapping("/partyboards")
    public ResponseEntity<ResponseMessage> registerPartyBoard(
            @ModelAttribute PartyBoardDTOForWrite newPartyBoardDTO
    ) {

        // 글 작성 후 자동 생성된 글 번호
        int registeredPartyBoardNumber = partyBoardService.registerPartyBoard(newPartyBoardDTO);


        /* 이미지 저장 임시 디렉터리 명 변경 */
        String tempDirStr = "src/main/resources/static/img/party_board/" + newPartyBoardDTO.getTempNumber();
        String newDirStr = "src/main/resources/static/img/party_board/" + registeredPartyBoardNumber;

        File tempDir = new File(tempDirStr);
        File newFileDir = new File(newDirStr);
        tempDir.renameTo(newFileDir);


        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "글 작성 성공", null));
    }

    /* 모임 글 수정 */
    @PutMapping("/partyboards/{partyBoardNumber}")
    public ResponseEntity<ResponseMessage> modifyPartyBoard(
            @PathVariable int partyBoardNumber,
            @ModelAttribute PartyBoardDTOForModify partyBoardDTO
    ) {
        // PathVariable의 partyBoardNumber를 DTO에 삽입
        partyBoardDTO.setPartyBoardNumber(partyBoardNumber);

        // form 태그로 테스트할 때 공백이 리스트에 삽입되는 것 방지
        partyBoardDTO.getRemovedFileNumbers().removeAll(partyBoardDTO.getRemovedFileNumbers());
        partyBoardDTO.getAddedFiles().removeAll(partyBoardDTO.getAddedFiles());

        partyBoardService.modifyPartyBoard(partyBoardDTO);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "글 수정 성공", null));
    }

    /* 모임 글 삭제 */
    @DeleteMapping("/partyboards/{partyBoardNumber}")
    public ResponseEntity<ResponseMessage> removePartyBoard(
            @PathVariable int partyBoardNumber
    ) {

        partyBoardService.removePartyBoard(partyBoardNumber);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "글 삭제 성공", null));
    }
}
