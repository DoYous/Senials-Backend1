package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "PARTY_BOARD_IMAGE")
public class PartyBoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_board_image_number", nullable = false)
    private int partyBoardImageNumber; // auto

    @ManyToOne
    @JoinColumn(name = "party_board_number", referencedColumnName = "party_board_number", nullable = false)
    private PartyBoard partyBoard; // PartyBoard와의 관계 설정

    @Column(name = "party_board_img", nullable = false, length = 255)
    private String partyBoardImg; // 이미지 경로
}
