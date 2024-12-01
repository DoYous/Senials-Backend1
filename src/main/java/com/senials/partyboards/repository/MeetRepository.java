package com.senials.partyboards.repository;

import com.senials.entity.Meet;
import com.senials.entity.PartyBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Integer> {

    List<Meet> findAllByPartyBoardOrderByMeetNumberDesc(PartyBoard partyBoard);
}
