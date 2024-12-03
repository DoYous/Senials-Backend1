package com.senials.partyboards.dto;

public class PartyBoardDTOForCardStatus extends PartyBoardDTOForCard {
    private boolean hasJoinedSchedule;

    public PartyBoardDTOForCardStatus(
            int partyBoardNumber,
            String partyBoardName,
            int partyBoardStatus,
            long memberCount,
            double averageRating,
            String firstImage,
            boolean hasJoinedSchedule
    ) {
        super(partyBoardNumber, partyBoardName, partyBoardStatus, memberCount, averageRating, firstImage);
        this.hasJoinedSchedule = hasJoinedSchedule;
    }
}
