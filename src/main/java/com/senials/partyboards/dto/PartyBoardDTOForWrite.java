package com.senials.partyboards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartyBoardDTOForWrite {

    private int userNumber;

    private int hobbyNumber;

    private String partyBoardName;

    private String partyBoardDetail;

    // 이미지 임시 저장 경로 번호
    private String tempNumber;

    // 이미지 이름 리스트
    private List<String> savedFiles;

    public PartyBoardDTOForWrite(int userNumber, int hobbyNumber, String partyBoardName, String partyBoardDetail, List<String> savedFiles) {
        this.userNumber = userNumber;
        this.hobbyNumber = hobbyNumber;
        this.partyBoardName = partyBoardName;
        this.partyBoardDetail = partyBoardDetail;
        this.savedFiles = savedFiles;
    }
}
