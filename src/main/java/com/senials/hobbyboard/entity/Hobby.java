package com.senials.hobbyboard.entity;

import jakarta.persistence.*;
import lombok.*;

//취미 테이블 entity
@Entity
@Table(name = "HOBBY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_number")
    private int hobbyNumber; // 취미 고유 번호

    @Column(name = "category_number", nullable = false)
    private int categoryNumber; // 카테고리 번호

    @Column(name = "hobby_name", nullable = false)
    private String hobbyName; // 취미 이름

    @Column(name = "hobby_explain", nullable = false)
    private String hobbyExplain; // 취미 설명

    @Column(name = "hobby_img", nullable = false)
    private String hobbyImg; // 취미 이미지 경로

    @Column(name = "hobby_ability", nullable = false)
    private int hobbyAbility; // 취미 요구 능력치

    @Column(name = "hobby_budget", nullable = false)
    private int hobbyBudget; // 취미 예산

    @Column(name = "hobby_level", nullable = false)
    private int hobbyLevel; // 취미 난이도

    @Column(name = "hobby_tendency", nullable = false)
    private int hobbyTendency; // 취미 성향
}
