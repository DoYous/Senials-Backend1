package com.senials.partyboards.service;

import com.senials.entity.PartyBoard;
import com.senials.entity.PartyReview;
import com.senials.entity.User;
import com.senials.partyboards.dto.PartyReviewDTO;
import com.senials.partyboards.mapper.PartyReviewMapper;
import com.senials.partyboards.mapper.PartyReviewMapperImpl;
import com.senials.partyboards.repository.PartyBoardRepository;
import com.senials.partyboards.repository.PartyReviewRepository;
import com.senials.partyboards.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartyReviewService {

    private final PartyReviewMapper partyReviewMapper;

    private final PartyReviewRepository partyReviewRepository;

    private final PartyBoardRepository partyBoardRepository;

    private final UserRepository userRepository;


    public PartyReviewService(
            PartyReviewMapperImpl partyReviewMapperImpl
            , PartyReviewRepository partyReviewRepository
            , PartyBoardRepository partyBoardRepository
            , UserRepository userRepository
    ) {
        this.partyReviewMapper = partyReviewMapperImpl;
        this.partyReviewRepository = partyReviewRepository;
        this.partyBoardRepository = partyBoardRepository;
        this.userRepository = userRepository;
    }


    /* 모임 후기 전체 조회 */
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

    /* 모임 후기 작성 */
    @Transactional
    public void registerPartyReview (
            int userNumber
            , int partyBoardNumber
            , PartyReviewDTO partyReviewDTO
    ) {

        User user = userRepository.findById(userNumber)
                .orElseThrow(IllegalArgumentException::new);

        PartyBoard partyBoard = partyBoardRepository.findById(partyBoardNumber)
                .orElseThrow(IllegalArgumentException::new);


        PartyReview partyReview = partyReviewMapper.toPartyReview(partyReviewDTO);
        partyReview.initializeUser(user);
        partyReview.initializePartyBoard(partyBoard);
        partyReview.initializePartyReviewWriteDate(LocalDateTime.now());


        partyReviewRepository.save(partyReview);
    }

    /* 모임 후기 수정 */
    @Transactional
    public void modifyPartyReview(
            int partyReviewNumber
            , PartyReviewDTO partyReviewDTO
    ) {
        PartyReview partyReview = partyReviewRepository.findById(partyReviewNumber)
                .orElseThrow(IllegalArgumentException::new);

        partyReview.updatePartyReviewRate(partyReviewDTO.getPartyReviewRate());
        partyReview.updatePartyReviewDetail(partyReviewDTO.getPartyReviewDetail());
    }

    /* 모임 후기 삭제 */
    @Transactional
    public void removePartyReview (int partyReviewNumber) {

        partyReviewRepository.deleteById(partyReviewNumber);
        
    }
}
