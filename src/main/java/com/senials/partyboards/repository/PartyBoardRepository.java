package com.senials.partyboards.repository;

import com.senials.entity.PartyBoard;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyBoardRepository extends JpaRepository<PartyBoard, Integer> {

    // 모임 이름으로 검색
    List<PartyBoard> findByPartyBoardNameContains(String boardName);

    // 모임 조회
    List<PartyBoard> findTopByPartyBoardName(String boardName, Sort sort);
}
