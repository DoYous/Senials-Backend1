package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "PARTY_BOARD")
public class PartyBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_board_number", nullable = false)
    private int partyBoardNumber; // auto

    @ManyToOne
    @JoinColumn(name = "user_number", referencedColumnName = "user_number", nullable = false)
    private User user; // 외래키 user_number -> User 엔티티와의 관계

    @ManyToOne
    @JoinColumn(name = "hobby_number", referencedColumnName = "hobby_number", nullable = false)
    private Hobby hobby; // 외래키 hobby_number -> Hobby 엔티티와의 관계

    @Column(name = "party_board_name", nullable = false, length = 255)
    private String partyBoardName;

    @Column(name = "party_board_detail", length = 5000)
    private String partyBoardDetail;

    @Temporal(TemporalType.DATE)
    @Column(name = "party_board_open_date", nullable = false)
    private LocalDate partyBoardOpenDate; // now()

    @Column(name = "party_board_status", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private int partyBoardStatus; // 0-모집중, 1-모집완료

    @Column(name = "party_board_view_cnt", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int partyBoardViewCnt = 0;

    @Column(name = "party_board_like_cnt", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int partyBoardLikeCnt = 0;

    @Column(name = "party_board_report_cnt", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int partyBoardReportCnt = 0;


    /* AllArgsConstructor */
    public PartyBoard(int partyBoardNumber, User user, Hobby hobby, String partyBoardName, String partyBoardDetail, LocalDate partyBoardOpenDate, int partyBoardStatus, int partyBoardViewCnt, int partyBoardLikeCnt, int partyBoardReportCnt) {
        this.partyBoardNumber = partyBoardNumber;
        this.user = user;
        this.hobby = hobby;
        this.partyBoardName = partyBoardName;
        this.partyBoardDetail = partyBoardDetail;
        this.partyBoardOpenDate = partyBoardOpenDate;
        this.partyBoardStatus = partyBoardStatus;
        this.partyBoardViewCnt = partyBoardViewCnt;
        this.partyBoardLikeCnt = partyBoardLikeCnt;
        this.partyBoardReportCnt = partyBoardReportCnt;
    }
}
