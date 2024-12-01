package com.senials.partyboards.service;

import com.senials.entity.Hobby;
import com.senials.entity.PartyBoardImage;
import com.senials.entity.User;
import com.senials.partyboards.dto.*;
import com.senials.entity.PartyBoard;
import com.senials.partyboards.mapper.PartyBoardMapper;
import com.senials.partyboards.mapper.PartyBoardMapperImpl;
import com.senials.partyboards.repository.HobbyRepository;
import com.senials.partyboards.repository.PartyBoardRepository;
import com.senials.partyboards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartyBoardService {

    private final String imageRootPath = "src/main/resources/static/img/party_board";

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
        partyBoardDTO.setImages(partyBoard.getImages().stream().map(image -> partyBoardMapper.toPartyBoardImageDTO(image)).toList());

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


    /* 모임 글 수정 */
    @Transactional
    public void modifyPartyBoard(PartyBoardDTOForModify partyBoardDTO) {

        int partyBoardNumber = partyBoardDTO.getPartyBoardNumber();

        /* 기존 엔티티 로드 */
        PartyBoard partyBoard = partyBoardRepository.findById(partyBoardNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invalid partyBoardNumber: " + partyBoardNumber));


        /* 수정 DTO 데이터 */
        int newHobbyNumber = partyBoardDTO.getHobbyNumber();
        String newPartyBoardName = partyBoardDTO.getPartyBoardName();
        String newPartyBoardDetail = partyBoardDTO.getPartyBoardDetail();
        int newPartyBoardStatus = partyBoardDTO.getPartyBoardStatus();
        List<Integer> removedFileNumbers = partyBoardDTO.getRemovedFileNumbers();
        List<String> addedFiles = partyBoardDTO.getAddedFiles();


        /* 1. hobbyNumber 수정 됐을 때 */
        if(partyBoard.getHobby().getHobbyNumber() != partyBoardDTO.getHobbyNumber()) {
            Hobby hobby = hobbyRepository.findById(partyBoardDTO.getHobbyNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid hobbyNumber: " + partyBoardDTO.getHobbyNumber()));

            partyBoard.updateHobby(hobby);
        }

        /* 나머지는 비교X */
        partyBoard.updatePartyBoardName(newPartyBoardName);
        partyBoard.updatePartyBoardDetail(newPartyBoardDetail);
        partyBoard.updatePartyBoardStatus(newPartyBoardStatus);


        List<PartyBoardImage> partyBoardImages = partyBoard.getImages();

        String imgBoardPath = imageRootPath + "/" + partyBoardNumber + "/thumbnail";
        /* 2. 이미지 삭제*/
        if (removedFileNumbers != null && !removedFileNumbers.isEmpty()) {
            // 역순으로 순회 (리스트 순회 중 삭제해도 문제 없도록) > Iterator로 순회하는 방법도 있음
            for (int i = partyBoardImages.size() - 1; i >= 0; i--) {
                PartyBoardImage partyboardImage = partyBoardImages.get(i);

                // 역순으로 순회 (리스트 순회 중 삭제해도 문제 없도록)
                for (int j = removedFileNumbers.size() - 1; j >= 0; j--) {
                    Integer removedFileNumber = removedFileNumbers.get(j);

                    // 이미지 번호와 제거한 이미지 번호가 일치할 때
                    if (partyboardImage.getPartyBoardImageNumber() == removedFileNumber) {

                        // PartyBoard 엔티티 이미지 리스트에서 제거
                        partyBoardImages.remove(partyboardImage);
                        // 엔티티에서 제거 완료한 이미지 번호는 더 이상 비교하지 않음
                        removedFileNumbers.remove(removedFileNumber);

                        /* 실제 파일 삭제 */
                        File removedFile = new File(imgBoardPath + "/" + partyboardImage.getPartyBoardImg());
                        removedFile.delete();
                        break;
                    }
                }
            }
        }
        
        /* 3. 이미지 추가 */
        if (addedFiles != null && !addedFiles.isEmpty()) {
            for (String addedFile : addedFiles) {
                PartyBoardImage partyBoardImage = PartyBoardImage.builder()
                        .partyBoard(partyBoard)
                        .partyBoardImg(addedFile)
                        .build();
                
                partyBoardImages.add(partyBoardImage);
            }
        }
    }


    /* 모임 글 삭제 */
    @Transactional
    public void removePartyBoard(int partyBoardNumber) {

        partyBoardRepository.deleteById(partyBoardNumber);
    }
}
