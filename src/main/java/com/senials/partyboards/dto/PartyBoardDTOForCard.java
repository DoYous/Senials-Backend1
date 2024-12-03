package com.senials.partyboards.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PartyBoardDTOForCard {
    private int partyBoardNumber;
    private String partyBoardName;
    private int partyBoardStatus;
    private long memberCount; // 멤버 수만 포함
}
