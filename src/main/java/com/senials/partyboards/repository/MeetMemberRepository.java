package com.senials.partyboards.repository;

import com.senials.entity.Meet;
import com.senials.entity.MeetMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetMemberRepository extends JpaRepository<MeetMember, Integer> {

    /* 모임 일정 참여멤버 조회 */
    List<MeetMember> findAllByMeet(Meet meet);
}
