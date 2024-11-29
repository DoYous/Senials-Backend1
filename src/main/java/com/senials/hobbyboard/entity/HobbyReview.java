package com.senials.hobbyboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

//취미 리뷰 entity
@Entity
@Table(name = "HOBBY_REVIEW")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HobbyReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_review_number")
    private int hobbyReviewNumber; // 취미 리뷰 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number", nullable = false)
    private User user; // 작성한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobby_number", nullable = false)
    private Hobby hobby; // 해당 취미 번호

    @Column(name = "hobby_review_rate", nullable = false)
    private int hobbyReviewRate; // 리뷰 평점 (1 ~ 5)

    @Column(name = "hobby_review_detail")
    private String hobbyReviewDetail; // 리뷰 상세 내용

    @Column(name = "hobby_review_health_status", nullable = false)
    private boolean hobbyReviewHealthStatus; // 건강 상태 (예: true: 예, false: 아니요)

    @Column(name = "hobby_review_tendency", nullable = false)
    private boolean hobbyReviewTendency; // 성향 (true: 외향적, false: 내향적)

    @Column(name = "hobby_review_level", nullable = false)
    private int hobbyReviewLevel; // 난이도 (1: 쉬움, 2: 약간 쉬움, 3: 평범, 4: 약간 어려움, 5: 어려움)

    @Column(name = "hobby_review_write_date", nullable = false)
    private LocalDate hobbyReviewWriteDate; // 리뷰 작성 날짜

}
