package com.senials.meetmembers.service;

import com.senials.common.entity.Meet;
import com.senials.common.entity.MeetMember;
import com.senials.common.entity.User;
import com.senials.common.mapper.UserMapper;
import com.senials.common.mapper.UserMapperImpl;
import com.senials.common.repository.MeetMemberRepository;
import com.senials.common.repository.MeetRepository;
import com.senials.user.dto.UserDTOForPublic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetMemberService {

    private final MeetMemberRepository meetMemberRepository;
    private final MeetRepository meetRepository;
    private final UserMapper userMapper;


    public MeetMemberService(
            MeetMemberRepository meetMemberRepository
            , MeetRepository meetRepository
            , UserMapperImpl userMapperImpl
    ) {
        this.meetMemberRepository = meetMemberRepository;
        this.meetRepository = meetRepository;
        this.userMapper = userMapperImpl;
    }


    /* 모임 일정 참여멤버 조회 */
    public List<UserDTOForPublic> getMeetMembersByMeetNumber(int meetNumber) {

        Meet meet = meetRepository.findById(meetNumber)
                .orElseThrow(IllegalArgumentException::new);

        /* 일정 참여 멤버 리스트 도출 */
        List<MeetMember> meetMemberList = meetMemberRepository.findAllByMeet(meet);

        /* 멤버 리스트 -> 유저 정보 도출 */
        List<User> userList = meetMemberList.stream()
                .map(meetMember -> meetMember.getPartyMember().getUser())
                .toList();

        /* DTO로 변환 */
        List<UserDTOForPublic> userDTOForPublicList = userList.stream()
                .map(userMapper::toUserDTOForPublic)
                .toList();

        return userDTOForPublicList;
    }
}
