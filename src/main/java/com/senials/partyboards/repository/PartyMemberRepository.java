package com.senials.partyboards.repository;

import com.senials.entity.PartyBoard;
import com.senials.entity.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Integer> {

    List<PartyMember> findAllByPartyBoard(PartyBoard partyBoard);

}
