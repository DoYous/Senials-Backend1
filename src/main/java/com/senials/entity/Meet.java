package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "MEET")
public class Meet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meet_number", nullable = false)
    private int meetNumber; // auto-increment

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_board_number", referencedColumnName = "party_board_number", nullable = false)
    private PartyBoard partyBoard; // 외래키 party_board_number -> PartyBoard 엔티티와의 관계

    @Column(name = "meet_start_date", nullable = false)
    private LocalDate meetStartDate;

    @Column(name = "meet_end_date", nullable = false)
    private LocalDate meetEndDate;

    @Column(name = "meet_start_time", nullable = false)
    private LocalTime meetStartTime;

    @Column(name = "meet_finsh_time", nullable = false)
    private LocalTime meetFinishTime;

    @Column(name = "meet_entry_fee", nullable = false)
    private int meetEntryFee = 0;

    @Column(name = "meet_location", nullable = false, length = 255)
    private String meetLocation;

    @Column(name = "meet_max_member", nullable = false)
    private int meetMaxMember = 0;

    @OneToMany(mappedBy = "meet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetMember> meetMembers;
}