package com.senials.partyboards.mapper;

import com.senials.common.entity.PartyBoardImage;
import com.senials.partyboards.dto.PartyBoardDTO;
import com.senials.common.entity.PartyBoard;
import com.senials.partyboards.dto.PartyBoardDTOForDetail;
import com.senials.partyboards.dto.PartyBoardImageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PartyBoardMapper {

    // PartyBoard -> PartyBoardDTO
    PartyBoardDTO toPartyBoardDTO(PartyBoard partyBoard);

    /* PartyBoardDTO -> PartyBoard */
    PartyBoard toPartyBoard(PartyBoardDTO partyBoardDTO);

    PartyBoardDTOForDetail toPartyBoardDTOForDetail(PartyBoard partyBoard);

    PartyBoardImageDTO toPartyBoardImageDTO(PartyBoardImage partyBoardImage);
}
