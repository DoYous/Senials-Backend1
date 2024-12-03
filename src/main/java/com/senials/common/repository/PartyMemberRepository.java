package com.senials.common.repository;

import com.senials.common.entity.PartyBoard;
import com.senials.common.entity.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Integer> {

    List<PartyMember> findAllByPartyBoard(PartyBoard partyBoard);

}
