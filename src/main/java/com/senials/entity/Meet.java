package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "MEET")
public class Meet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meet_number", nullable = false)
    private int meetNumber;

    @ManyToOne
    @JoinColumn(name = "party_board_number", referencedColumnName = "party_board_number", nullable = false)
    private PartyBoard partyBoard; // PartyBoard와의 관계

    @Column(name = "meet_start_date", nullable = false)
    private LocalDate meetStartDate;

    @Column(name = "meet_end_date", nullable = false)
    private LocalDate meetEndDate;

    @Column(name = "meet_start_time", nullable = false)
    private LocalTime meetStartTime;

    @Column(name = "meet_finsh_time", nullable = false)
    private LocalTime meetFinishTime;

    @Column(name = "meet_entry_fee", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int meetEntryFee;

    @Column(name = "meet_location", nullable = false, length = 255)
    private String meetLocation;

    @Column(name = "meet_max_member", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int meetMaxMember;


    /* AllArgsConstructor */
    public Meet(int meetNumber, PartyBoard partyBoard, LocalDate meetStartDate, LocalDate meetEndDate,
                LocalTime meetStartTime, LocalTime meetFinishTime, int meetEntryFee, String meetLocation, int meetMaxMember) {
        this.meetNumber = meetNumber;
        this.partyBoard = partyBoard;
        this.meetStartDate = meetStartDate;
        this.meetEndDate = meetEndDate;
        this.meetStartTime = meetStartTime;
        this.meetFinishTime = meetFinishTime;
        this.meetEntryFee = meetEntryFee;
        this.meetLocation = meetLocation;
        this.meetMaxMember = meetMaxMember;
    }
}
