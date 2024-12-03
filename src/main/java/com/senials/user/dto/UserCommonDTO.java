package com.senials.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public class UserCommonDTO {

  /*      private int userNumber;*/

        private String userName;

        private String userNickname;

        private String userDetail;

        private String userProfileImg;


        /* AllArgsConstructor */
        public UserCommonDTO(int userNumber,String userName,
                       String userNickname, String userDetail, String userProfileImg) {
            /*this.userNumber = userNumber;*/
            this.userName = userName;
            this.userNickname = userNickname;
            this.userDetail = userDetail;
            this.userProfileImg = userProfileImg;}

            // 새로운 생성자 추가
        public UserCommonDTO(String userName, String userNickname, String userDetail, String userProfileImg) {
                this.userName = userName;
                this.userNickname = userNickname;
                this.userDetail = userDetail;
                this.userProfileImg = userProfileImg;
            }

    }

