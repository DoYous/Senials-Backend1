package com.senials.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "MEET_MEMBER")
public class MeetMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meet_member_number", nullable = false)
    private int meetMemberNumber; // auto-increment

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meet_number", referencedColumnName = "meet_number", nullable = false)
    private Meet meet; // 외래키 meet_number -> Meet 엔티티와의 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_member_number", referencedColumnName = "party_member_number", nullable = false)
    private PartyMember partyMember; // 외래키 party_member_number -> PartyMember 엔티티와의 관계
}
