package com.senials.partyboards.service;

import com.senials.entity.PartyBoard;
import com.senials.entity.PartyReview;
import com.senials.partyboards.dto.PartyReviewDTO;
import com.senials.partyboards.mapper.PartyReviewMapper;
import com.senials.partyboards.mapper.PartyReviewMapperImpl;
import com.senials.partyboards.repository.PartyBoardRepository;
import com.senials.partyboards.repository.PartyReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final PartyReviewMapper partyReviewMapper;

    private final PartyReviewRepository partyReviewRepository;

    private final PartyBoardRepository partyBoardRepository;


    public ReviewService(
            PartyReviewMapperImpl partyReviewMapperImpl
            , PartyReviewRepository partyReviewRepository
            , PartyBoardRepository partyBoardRepository
    ) {
        this.partyReviewMapper = partyReviewMapperImpl;
        this.partyReviewRepository = partyReviewRepository;
        this.partyBoardRepository = partyBoardRepository;
    }


    public List<PartyReviewDTO> getPartyReviewsByPartyBoardNumber(int partyBoardNumber) {

        PartyBoard partyBoard = partyBoardRepository.findById(partyBoardNumber)
                .orElseThrow(IllegalArgumentException::new);

        /* 모임 번호에 해당하는 모임 후기 리스트 도출 */
        List<PartyReview> partyReviewList = partyReviewRepository.findAllByPartyBoardOrderByPartyReviewWriteDateDesc(partyBoard);

        List<PartyReviewDTO> partyReviewDTOList = partyReviewList.stream()
                .map(partyReviewMapper::toPartyReviewDTO)
                .toList();

        return partyReviewDTOList;
    }
}
