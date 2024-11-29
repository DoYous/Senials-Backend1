package com.senials.hobbyboard.entity;

import jakarta.persistence.*;
import lombok.*;

//유저 entity
@Entity
@Table(name = "USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_number")
    private int userNumber; // 사용자 고유 번호

    @Column(name = "user_pwd", nullable = false)
    private String userPwd; // 사용자 비밀번호

    @Column(name = "user_name", nullable = false)
    private String userName; // 사용자 이름

    @Column(name = "user_birth", nullable = false)
    private String userBirth; // 사용자 생년월일

    @Column(name = "user_email", nullable = false)
    private String userEmail; // 사용자 이메일

    @Column(name = "user_gender", nullable = false)
    private boolean userGender; // 사용자 성별 (0: 남, 1: 여)

    @Column(name = "user_report_cnt", nullable = false)
    private int userReportCnt; // 사용자 신고 횟수

    @Column(name = "user_status", nullable = false)
    private int userStatus; // 사용자 상태 (0: 기본, 1: 임시 활동 정지, 2: 활동 정지)

    @Column(name = "user_nickname", nullable = false)
    private String userNickname; // 사용자 닉네임

    @Column(name = "user_detail")
    private String userDetail; // 사용자 상세 정보

    @Column(name = "user_profile_img")
    private String userProfileImg; // 사용자 프로필 이미지 URL / 경로

    @Column(name = "user_signup_date", nullable = false)
    private String userSignupDate; // 사용자 가입 날짜

    @Column(name = "user_uuid", nullable = false)
    private String userUuid; // 사용자 UUID
}
