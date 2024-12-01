package com.senials.partyboards.service;

import com.senials.entity.Hobby;
import com.senials.entity.PartyBoardImage;
import com.senials.entity.User;
import com.senials.partyboards.dto.PartyBoardDTO;
import com.senials.entity.PartyBoard;
import com.senials.partyboards.dto.PartyBoardDTOForDetail;
import com.senials.partyboards.dto.PartyBoardDTOForWrite;
import com.senials.partyboards.mapper.PartyBoardMapper;
import com.senials.partyboards.mapper.PartyBoardMapperImpl;
import com.senials.partyboards.repository.HobbyRepository;
import com.senials.partyboards.repository.PartyBoardRepository;
import com.senials.partyboards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartyBoardService {

    private final PartyBoardMapper partyBoardMapper;

    private final PartyBoardRepository partyBoardRepository;

    private final UserRepository userRepository;

    private final HobbyRepository hobbyRepository;

    @Autowired
    public PartyBoardService(PartyBoardMapperImpl partyBoardMapperImpl, PartyBoardRepository partyBoardRepository, UserRepository userRepository, HobbyRepository hobbyRepository) {
        this.partyBoardMapper = partyBoardMapperImpl;
        this.partyBoardRepository = partyBoardRepository;
        this.userRepository = userRepository;
        this.hobbyRepository = hobbyRepository;
    }


    // public List<PartyBoardDTO> searchPartySorting(String keyword, String sortColumn, String compareChar, String sortDirection, Integer cursor) {
    //
    //
    //     List<PartyBoard> partyBoardList = partyBoardRepository.findTopByPartyBoardName(keyword, sortColumn, compareChar, sortDirection, cursor);
    //
    //     List<PartyBoardDTO> partyBoardDTOList = partyBoardList.stream().map(partyBoard -> partyBoardMapper.toPartyBoardDTO(partyBoard)).toList();
    //
    //     return partyBoardDTOList;
    // }

    // 모임 상세 조회
    public PartyBoardDTOForDetail getPartyBoardByNumber(int partyBoardNumber) {

        PartyBoard partyBoard = partyBoardRepository.findByPartyBoardNumber(partyBoardNumber);

        PartyBoardDTOForDetail partyBoardDTO = partyBoardMapper.toPartyBoardDTOForDetail(partyBoard);
        partyBoardDTO.setUserNumber(partyBoard.getUser().getUserNumber());

        return partyBoardDTO;
    }

    /* 모임 글 작성 */
    @Transactional
    public int registerPartyBoard(PartyBoardDTOForWrite newPartyBoardDTO) {

        // 1. userNumber로 User 엔티티 조회
        User user = userRepository.findById(newPartyBoardDTO.getUserNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid userNumber: " + newPartyBoardDTO.getUserNumber()));

        // 2. hobbyNumber로 Hobby 엔티티 조회
        Hobby hobby = hobbyRepository.findById(newPartyBoardDTO.getHobbyNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid hobbyNumber: " + newPartyBoardDTO.getHobbyNumber()));

        // 3. PartyBoard 엔티티 생성
        PartyBoard newPartyBoard = new PartyBoard(
                0, // 기본키
                user,
                hobby,
                newPartyBoardDTO.getPartyBoardName(),
                newPartyBoardDTO.getPartyBoardDetail(),
                LocalDate.now(),
                0,
                0,
                0,
                0
        );

        // 4. 이미지 저장
        List<PartyBoardImage> images = new ArrayList<>();
        for (String savedFile : newPartyBoardDTO.getSavedFiles()) {
            PartyBoardImage partyBoardImage = PartyBoardImage.builder()
                    // partyBoard 엔티티와 연결
                    .partyBoard(newPartyBoard)
                    .partyBoardImg(savedFile)
                    .build();

            images.add(partyBoardImage);
        }

        newPartyBoard.initializeImages(images);

        // 4. 엔티티 저장
        PartyBoard registeredPartyBoard = partyBoardRepository.save(newPartyBoard);
        return registeredPartyBoard.getPartyBoardNumber();
    }
}
