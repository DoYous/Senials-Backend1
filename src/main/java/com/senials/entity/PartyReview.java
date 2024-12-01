package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "PARTY_REVIEW")
public class PartyReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_review_number", nullable = false)
    private int partyReviewNumber; // 기본키, auto-increment

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_number", referencedColumnName = "user_number", nullable = false)
    private User user; // 외래키 user_number -> User 엔티티와의 관계

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party_board_number", referencedColumnName = "party_board_number", nullable = false)
    private PartyBoard partyBoard; // 외래키 party_board_number -> PartyBoard 엔티티와의 관계

    @Column(name = "party_review_rate", nullable = false)
    private int partyReviewRate; // 1~5

    @Column(name = "party_review_detail", length = 5000)
    private String partyReviewDetail; // 리뷰 상세 내용

    @Column(name = "party_review_write_date", nullable = false)
    private LocalDateTime partyReviewWriteDate; // 작성 날짜

    /* AllArgsConstructor */
    public PartyReview(int partyReviewNumber, User user, PartyBoard partyBoard, int partyReviewRate, String partyReviewDetail, LocalDateTime partyReviewWriteDate) {
        this.partyReviewNumber = partyReviewNumber;
        this.user = user;
        this.partyBoard = partyBoard;
        this.partyReviewRate = partyReviewRate;
        this.partyReviewDetail = partyReviewDetail;
        this.partyReviewWriteDate = partyReviewWriteDate;
    }
}
