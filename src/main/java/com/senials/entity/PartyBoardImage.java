package com.senials.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "PARTY_BOARD_IMAGE")
public class PartyBoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_board_image_number")
    private int partyBoardImageNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_board_number", nullable = false)
    private PartyBoard partyBoard;

    @Column(name = "party_board_img", nullable = false, length = 255)
    private String partyBoardImg;

    /* AllArgsConstructor */
    @Builder
    public PartyBoardImage(int partyBoardImageNumber, PartyBoard partyBoard, String partyBoardImg) {
        this.partyBoardImageNumber = partyBoardImageNumber;
        this.partyBoard = partyBoard;
        this.partyBoardImg = partyBoardImg;
    }
}

