package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_number", nullable = false)
    private int userNumber;

    @Column(name = "user_pwd", nullable = false, length = 255)
    private String userPwd;

    @Column(name = "user_name", nullable = false, length = 255)
    private String userName;

    @Column(name = "user_birth", nullable = false, length = 255)
    private LocalDate userBirth;

    @Column(name = "user_email", nullable = false, length = 255)
    private String userEmail;

    @Column(name = "user_gender", nullable = false)
    private int userGender; // 0-남, 1-여

    @Column(name = "user_report_cnt", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int userReportCnt;

    @Column(name = "user_status", nullable = false)
    private int userStatus; // 0-기본, 1-임시활동정지, 2-활동정지

    @Column(name = "user_nickname", nullable = false, length = 255)
    private String userNickname;

    @Column(name = "user_detail", length = 255)
    private String userDetail;

    @Column(name = "user_profile_img", length = 5000)
    private String userProfileImg;

    @Column(name = "user_signup_date", nullable = false)
    private LocalDate userSignupDate;

    @Column(name = "user_uuid", nullable = false, length = 5000)
    private String userUuid;

    // 관계 설정
    @OneToMany(mappedBy = "user")
    private List<PartyBoard> partyBoards; // User -> PartyBoard 관계 (1:N)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyMember> partyMemberShip;

    /* AllArgsConstructor */
    public User(int userNumber, String userPwd, String userName, LocalDate userBirth, String userEmail, int userGender, int userReportCnt, int userStatus, String userNickname, String userDetail, String userProfileImg, LocalDate userSignupDate, String userUuid, List<PartyBoard> partyBoards) {
        this.userNumber = userNumber;
        this.userPwd = userPwd;
        this.userName = userName;
        this.userBirth = userBirth;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userReportCnt = userReportCnt;
        this.userStatus = userStatus;
        this.userNickname = userNickname;
        this.userDetail = userDetail;
        this.userProfileImg = userProfileImg;
        this.userSignupDate = userSignupDate;
        this.userUuid = userUuid;
        this.partyBoards = partyBoards;
    }
}
