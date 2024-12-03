package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "PARTY_MEMBER")
public class PartyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_member_number", nullable = false)
    private int partyMemberNumber; // auto

    @ManyToOne
    @JoinColumn(name = "party_board_number", nullable = false)
    private PartyBoard partyBoard; // 모임 게시판과 관계

    @ManyToOne
    @JoinColumn(name = "user_number", nullable = false)
    private User user; // 사용자와 관계
}
