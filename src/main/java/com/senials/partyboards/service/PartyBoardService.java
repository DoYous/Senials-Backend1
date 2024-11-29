package com.senials.partyboards.service;

import com.senials.partyboards.dto.PartyBoardDTO;
import com.senials.entity.PartyBoard;
import com.senials.partyboards.mapper.PartyBoardMapper;
import com.senials.partyboards.mapper.PartyBoardMapperImpl;
import com.senials.partyboards.repository.PartyBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyBoardService {

    private PartyBoardMapper partyBoardMapper;

    private PartyBoardRepository partyBoardRepository;

    public PartyBoardService(PartyBoardMapperImpl partyBoardMapperImpl, PartyBoardRepository partyBoardRepository) {
        this.partyBoardMapper = partyBoardMapperImpl;
        this.partyBoardRepository = partyBoardRepository;
    }

    public List<PartyBoardDTO> searchPartyBoard(String partyBoardName) {
        // Sort sort = Sort.by()

        List<PartyBoard> partyBoardList = partyBoardRepository.findByPartyBoardNameContains(partyBoardName);

        List<PartyBoardDTO> partyBoardDTOList = partyBoardList.stream().map(partyBoard -> partyBoardMapper.toPartyBoardDTO(partyBoard)).toList();

        return partyBoardDTOList;
    }
}
