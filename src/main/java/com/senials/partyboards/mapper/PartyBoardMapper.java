package com.senials.partyboards.mapper;

import com.senials.partyboards.dto.PartyBoardDTO;
import com.senials.entity.PartyBoard;
import com.senials.partyboards.dto.PartyBoardDTOForDetail;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PartyBoardMapper {

    // PartyBoard -> PartyBoardDTO
    PartyBoardDTO toPartyBoardDTO(PartyBoard partyBoard);

    /* PartyBoardDTO -> PartyBoard */
    PartyBoard toPartyBoard(PartyBoardDTO partyBoardDTO);

    PartyBoardDTOForDetail toPartyBoardDTOForDetail(PartyBoard partyBoard);


}
