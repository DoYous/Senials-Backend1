package com.senials.common.repository;

import com.senials.common.entity.PartyBoard;
import com.senials.common.entity.PartyReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyReviewRepository extends JpaRepository<PartyReview, Integer> {

    List<PartyReview> findAllByPartyBoardOrderByPartyReviewWriteDateDesc(PartyBoard partyBoard);



}
