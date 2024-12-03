package com.senials.partymembers.service;

import com.senials.common.entity.PartyBoard;
import com.senials.common.entity.PartyMember;
import com.senials.common.entity.User;
import com.senials.common.mapper.UserMapperImpl;
import com.senials.common.repository.PartyBoardRepository;
import com.senials.common.repository.PartyMemberRepository;
import com.senials.common.repository.UserRepository;
import com.senials.user.dto.UserDTOForPublic;
import com.senials.common.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartyMemberService {

    private final PartyBoardRepository partyBoardRepository;
    private final PartyMemberRepository partyMemberRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public PartyMemberService(
            PartyBoardRepository partyBoardRepository
            , PartyMemberRepository partyMemberRepository
            , UserRepository userRepository
            , UserMapperImpl userMapperImpl) {
        this.partyBoardRepository = partyBoardRepository;
        this.partyMemberRepository = partyMemberRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapperImpl;
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

}
