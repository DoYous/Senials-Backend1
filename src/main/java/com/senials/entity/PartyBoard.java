package com.senials.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "partyBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meet> meets; // PartyBoard -> Meet 관계 (1:N)

    @OneToMany(mappedBy = "partyBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("partyBoardImageNumber ASC")
    private List<PartyBoardImage> images;

    @OneToMany(mappedBy = "partyBoard", fetch = FetchType.LAZY)
    private List<PartyReview> partyReviews;

    @OneToMany(mappedBy = "partyBoard")
    private List<PartyMember> partyMembers; // 참여 인원

    @OneToMany(mappedBy = "partyBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;

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
