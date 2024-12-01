package com.senials.partyboards.service;

import com.senials.entity.Meet;
import com.senials.entity.PartyBoard;
import com.senials.partyboards.dto.MeetDTO;
import com.senials.partyboards.mapper.MeetMapper;
import com.senials.partyboards.mapper.MeetMapperImpl;
import com.senials.partyboards.repository.MeetRepository;
import com.senials.partyboards.repository.PartyBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeetService {

    private final MeetMapper meetMapper;

    private final PartyBoardRepository partyBoardRepository;

    private final MeetRepository meetRepository;

    @Autowired
    public MeetService(MeetMapperImpl meetMapperImpl, PartyBoardRepository partyBoardRepository, MeetRepository meetRepository) {
        this.meetMapper = meetMapperImpl;
        this.partyBoardRepository = partyBoardRepository;
        this.meetRepository = meetRepository;
    }


    /* 모임 내 일정 전체 조회 */
    public List<MeetDTO> getMeetsByPartyBoardNumber(int partyBoardNumber) {

        PartyBoard partyBoard = partyBoardRepository.findById(partyBoardNumber)
                .orElseThrow(IllegalArgumentException::new);

        List<Meet> meetList = meetRepository.findAllByPartyBoardOrderByMeetNumberDesc(partyBoard);

        List<MeetDTO> meetDTOList = meetList.stream().map(meetMapper::toMeetDTO).toList();

        return meetDTOList;
    }


    /* 모임 일정 추가 */
    @Transactional
    public void registerMeet(int partyBoardNumber, MeetDTO meetDTO) {

        PartyBoard partyBoard = partyBoardRepository.findById(partyBoardNumber)
                .orElseThrow(IllegalArgumentException::new);

        Meet meet = meetMapper.toMeet(meetDTO);

        /* PartyBoard 엔티티 연결 */
        meet.initializePartyBoard(partyBoard);

        meetRepository.save(meet);
    }


    /* 모임 일정 수정 */
    @Transactional
    public void modifyMeet(int meetNumber, MeetDTO meetDTO) {
        /* 기존 일정 엔티티 로드 */
        Meet meet = meetRepository.findById(meetNumber)
                .orElseThrow(IllegalArgumentException::new);


        /* 컬럼 변경 별도 검증 없이 모두 갱신*/
        meet.updateAll(meetDTO);
    }


    /* 모임 일정 삭제 */
    @Transactional
    public void removeMeet(int meetNumber) {

        meetRepository.deleteById(meetNumber);

    }

}
