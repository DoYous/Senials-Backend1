package com.senials.partyboards.repository;

import com.senials.entity.PartyBoard;
import com.senials.entity.PartyReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyReviewRepository extends JpaRepository<PartyReview, Integer> {

    List<PartyReview> findAllByPartyBoardOrderByPartyReviewWriteDateDesc(PartyBoard partyBoard);
}
