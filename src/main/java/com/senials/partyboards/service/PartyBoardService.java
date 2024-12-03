package com.senials.partyboards.service;

import com.senials.common.entity.*;
import com.senials.common.repository.*;
import com.senials.partyboards.dto.*;
import com.senials.partyboards.mapper.PartyBoardMapper;
import com.senials.partyboards.mapper.PartyBoardMapperImpl;
import com.senials.partyboards.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartyBoardService {

    private final String imageRootPath = "src/main/resources/static/img/party_board";

    private final UserMapper userMapper;

    private final PartyBoardMapper partyBoardMapper;

    private final PartyBoardRepository partyBoardRepository;

    private final PartyMemberRepository partyMemberRepository;

    private final UserRepository userRepository;

    private final HobbyRepository hobbyRepository;

    private final FavoritesRepository favoritesRepository;


    @Autowired
    public PartyBoardService(
            UserMapper userMapper
            , PartyBoardMapperImpl partyBoardMapperImpl
            , PartyBoardRepository partyBoardRepository
            , PartyMemberRepository partyMemberRepository
            , UserRepository userRepository
            , HobbyRepository hobbyRepository
            , FavoritesRepository favoritesRepository
    ) {
        this.userMapper = userMapper;
        this.partyBoardMapper = partyBoardMapperImpl;
        this.partyBoardRepository = partyBoardRepository;
        this.partyMemberRepository = partyMemberRepository;
        this.userRepository = userRepository;
        this.hobbyRepository = hobbyRepository;
        this.favoritesRepository = favoritesRepository;
    }


    /* 모임 검색 및 정렬 */
    public List<PartyBoardDTOForDetail> searchPartyBoard(String sortMethod, String keyword, Integer cursor, int size, boolean isLikedOnly) {

        Sort.Order numberAsc = Sort.Order.asc("partyBoardNumber");
        Sort.Order numberDesc = Sort.Order.desc("partyBoardNumber");

        String sortColumn = null;
        Pageable pageable = null;
        boolean isAscending = false;
        boolean isIntegerSort = true;

        switch (sortMethod) {
            /* 최신순 */
            case "lastest":
                sortColumn = "partyBoardOpenDate";
                pageable = PageRequest.of(0, size
                        , Sort.by(Sort.Order.desc(sortColumn), numberDesc));
                isIntegerSort = false;
                break;

            /* 오래된 순*/
            case "oldest":
                sortColumn = "partyBoardOpenDate";
                pageable = PageRequest.of(0, size
                        , Sort.by(Sort.Order.asc(sortColumn), numberAsc));
                isAscending = true;
                isIntegerSort = false;
                break;

            /* 좋아요순 */
            case "mostLiked":
                sortColumn = "partyBoardLikeCnt";
                pageable = PageRequest.of(0, size
                        , Sort.by(Sort.Order.desc(sortColumn), numberDesc));
                break;

            /* 조회수순 */
            case "mostViewed":
                sortColumn = "partyBoardViewCnt";
                pageable = PageRequest.of(0, size
                        , Sort.by(Sort.Order.desc(sortColumn), numberDesc));
                break;
            default:
        }


        /* 관심사 기반 추천 확인 */
        // 관심사 기반 추천 시 최소 빈 리스트 / 미추천 시 null
        List<Hobby> hobbyList = null;
        if(isLikedOnly) {
            /* 유저 number 필요 */ int userNumber = 3;
            User user = userRepository.findById(userNumber).orElseThrow(IllegalArgumentException::new);
            List<Favorites> favoritesList = favoritesRepository.findAllByUser(user);

            /* 관심사 존재하는지 체크 */
            if(!favoritesList.isEmpty()) {
                hobbyList = favoritesList.stream().map(Favorites::getHobby).toList();
            } else {
                hobbyList = new ArrayList<>();
            }
        }

        /* 첫 페이지 로딩 OR 정렬 변경 직후 */
        Page<PartyBoard> partyBoardList = null;
        if(cursor == null) {
            if(hobbyList == null) {
                partyBoardList = partyBoardRepository.findAll(pageable);
            } else {
                partyBoardList = partyBoardRepository.findAllByHobbyIn(hobbyList, pageable);
            }

        /* 더보기 버튼으로 로드 */
        } else {
            Specification<PartyBoard> spec = null;

            if(isIntegerSort) {
                spec = PartyBoardSpecification.searchLoadInteger(sortColumn, keyword, cursor, isAscending, hobbyList);
            } else {
                spec = PartyBoardSpecification.searchLoadLocalDate(sortColumn, keyword, cursor, isAscending, hobbyList);
            }

            partyBoardList = partyBoardRepository.findAll(spec, pageable);

        }

        return partyBoardList.map(partyBoardMapper::toPartyBoardDTOForDetail).toList();
    }


    // 모임 상세 조회
    public PartyBoardDTOForDetail getPartyBoardByNumber(int partyBoardNumber) {

        PartyBoard partyBoard = partyBoardRepository.findByPartyBoardNumber(partyBoardNumber);

        PartyBoardDTOForDetail partyBoardDTO = partyBoardMapper.toPartyBoardDTOForDetail(partyBoard);
        partyBoardDTO.setUserNumber(partyBoard.getUser().getUserNumber());
        partyBoardDTO.setImages(partyBoard.getImages().stream().map(image -> partyBoardMapper.toPartyBoardImageDTO(image)).toList());

        return partyBoardDTO;
    }

    /* 모임 멤버 전체 조회 */
    public List<UserDTOForPublic> getPartyMembers (int partyBoardNumber) {

        PartyBoard partyBoard = partyBoardRepository.findById(partyBoardNumber)
                .orElseThrow(IllegalArgumentException::new);

        /* 모임 번호에 해당하는 멤버 리스트 도출 */
        List<PartyMember> partyMemberList = partyMemberRepository.findAllByPartyBoard(partyBoard);

        /* 멤버 리스트를 통해 유저 정보 도출 */
        List<UserDTOForPublic> userDTOForPublicList = partyMemberList.stream()
                .map(partyMember -> userMapper.toUserDTOForPublic(partyMember.getUser()))
                .toList();

        return userDTOForPublicList;
    }


    /* 모임 참가 */
    @Transactional
    public void registerPartyMember (int userNumber, int partyBoardNumber) {
        User user = userRepository.findById(userNumber)
                .orElseThrow(IllegalArgumentException::new);

        PartyBoard partyBoard = partyBoardRepository.findById(partyBoardNumber)
                .orElseThrow(IllegalArgumentException::new);

        PartyMember newMember = new PartyMember(0, partyBoard, user);

        partyBoard.registerPartyMember(newMember);

    }


    /* 모임 탈퇴 */
    @Transactional
    public void unregisterPartyMember (int partyMemberNumber) {

        partyMemberRepository.deleteById(partyMemberNumber);

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
