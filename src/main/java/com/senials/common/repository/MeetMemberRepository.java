package com.senials.common.repository;

import com.senials.common.entity.Meet;
import com.senials.common.entity.MeetMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetMemberRepository extends JpaRepository<MeetMember, Integer> {

    /* 모임 일정 참여멤버 조회 */
    List<MeetMember> findAllByMeet(Meet meet);
}
