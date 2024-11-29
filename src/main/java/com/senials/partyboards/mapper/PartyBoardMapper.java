package com.senials.partyboards.mapper;

import com.senials.partyboards.dto.PartyBoardDTO;
import com.senials.entity.PartyBoard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartyBoardMapper {

    // PartyBoard -> PartyBoardDTO
    PartyBoardDTO toPartyBoardDTO(PartyBoard partyBoard);

}
