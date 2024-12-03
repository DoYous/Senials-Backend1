package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "LIKES")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_number", nullable = false)
    private int likeNumber;

    @ManyToOne
    @JoinColumn(name = "user_number", referencedColumnName = "user_number", nullable = false)
    private User user; // 외래키 user_number -> User 엔티티와의 관계

    @ManyToOne
    @JoinColumn(name = "party_board_number", referencedColumnName = "party_board_number", nullable = false)
    private PartyBoard partyBoard; // 외래키 party_board_number -> PartyBoard 엔티티와의 관계

    /* AllArgsConstructor */
    public Likes(int likeNumber, User user, PartyBoard partyBoard) {
        this.likeNumber = likeNumber;
        this.user = user;
        this.partyBoard = partyBoard;
    }
}
