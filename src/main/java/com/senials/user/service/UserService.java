package com.senials.user.service;

import com.senials.entity.PartyBoard;
import com.senials.entity.PartyMember;
import com.senials.entity.PartyReview;
import com.senials.entity.User;
import com.senials.partyboards.dto.PartyBoardDTOForCard;
import com.senials.user.dto.UserCommonDTO;
import com.senials.user.dto.UserDTO;
import com.senials.user.mapper.UserMapper;
import com.senials.partyMember.repository.PartyMemberRepository;
import com.senials.partyboards.dto.PartyBoardDTO;
import com.senials.partyboards.repository.PartyBoardRepository;
import com.senials.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserMapper userMapper;

    private com.senials.user.repository.UserRepository userRepository;
    private final PartyMemberRepository partyMemberRepository;
    private final PartyBoardRepository partyBoardRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository, PartyMemberRepository partyMemberRepository, PartyBoardRepository partyBoardRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.partyMemberRepository = partyMemberRepository;
        this.partyBoardRepository = partyBoardRepository;
    }

    //모든 사용자 조회
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toMypageDTO)
                .toList();

    }
    // 특정 사용자 조회
    public UserCommonDTO getUserByNumber(int userNumber) {
        return userRepository.findById(userNumber)
                .map(user -> new UserCommonDTO(
                        user.getUserName(),
                        user.getUserNickname(),
                        user.getUserDetail(),
                        user.getUserProfileImg()
                ))
                .orElse(null);
    }

    //특정 사용자 탈퇴
    public boolean deleteUser(int userNumber) {
        if (userRepository.existsById(userNumber)) {
            userRepository.deleteById(userNumber);
            return true;
        }
        return false; // 사용자 존재하지 않음
    }



    // 특정 사용자 수정
    public boolean updateUserProfile(int userNumber, String userNickname, String userDetail) {
        return userRepository.findById(userNumber).map(existingUser -> {
            if (userNickname != null) {
                existingUser.setUserNickname(userNickname);
            }
            if (userDetail != null) {
                existingUser.setUserDetail(userDetail);
            }
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }


    //사용자별 참여한 모임 출력
    public Map<String, Object> getUserParties(int userNumber) {
        List<PartyMember> partyMembers = partyMemberRepository.findByUser_UserNumber(userNumber);

        // PartyBoardDTO 리스트 생성
        List<PartyBoardDTO> partyList = partyMembers.stream()
                .map(pm -> {
                    PartyBoardDTO dto = new PartyBoardDTO();
                    dto.setPartyBoardNumber(pm.getPartyBoard().getPartyBoardNumber());
                    dto.setUserNumber(pm.getPartyBoard().getUser().getUserNumber());
                    dto.setHobbyNumber(pm.getPartyBoard().getHobby().getHobbyNumber());
                    dto.setPartyBoardName(pm.getPartyBoard().getPartyBoardName());
                    dto.setPartyBoardDetail(pm.getPartyBoard().getPartyBoardDetail());
                    dto.setPartyBoardOpenDate(pm.getPartyBoard().getPartyBoardOpenDate());
                    dto.setPartyBoardStatus(pm.getPartyBoard().getPartyBoardStatus());
                    dto.setPartyBoardViewCnt(pm.getPartyBoard().getPartyBoardViewCnt());
                    dto.setPartyBoardLikeCnt(pm.getPartyBoard().getPartyBoardLikeCnt());
                    dto.setPartyBoardReportCnt(pm.getPartyBoard().getPartyBoardReportCnt());
                    return dto;
                })
                .toList();

        // Map으로 변환
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userNumber", userNumber);
        resultMap.put("parties", partyList);

        return resultMap;
    }
    // 사용자가 만든 모임 목록 조회
    public List<PartyBoardDTOForCard> getMadePartyBoardsByUserNumber(int userNumber) {
        // User가 만든 PartyBoard 목록을 직접 조회
        User user = userRepository.findById(userNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<PartyBoard> partyBoards = user.getPartyBoards(); // User와 PartyBoard가 연결되어 있다고 가정

        return partyBoards.stream().map(partyBoard -> {
            // 별점 평균 계산
            double averageRating = 0.0;
            List<PartyReview> reviews = partyBoard.getPartyReviews(); // PartyBoard에 PartyReview 관계가 있다고 가정

            if (!reviews.isEmpty()) {
                int totalRating = 0;
                for (PartyReview review : reviews) {
                    totalRating += review.getPartyReviewRate();
                }
                averageRating = (double) totalRating / reviews.size();
            }

            // 첫 번째 이미지 가져오기
            String firstImage = partyBoard.getImages().isEmpty() ? null : partyBoard.getImages().get(0).getPartyBoardImg();

            // DTO 생성
            return new PartyBoardDTOForCard(
                    partyBoard.getPartyBoardNumber(),
                    partyBoard.getPartyBoardName(),
                    partyBoard.getPartyBoardStatus(),
                    partyBoard.getPartyMembers().size(), // 참여 인원 수
                    averageRating,
                    firstImage
            );
        }).collect(Collectors.toList());
    }



}
