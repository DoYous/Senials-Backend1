package com.senials.hobbyboard.mapper;

import com.senials.entity.HobbyReview;
import com.senials.hobbyboard.dto.HobbyReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HobbyReviewMapper {

    // 기존 매핑 설정에 커스텀 변환기 적용
    @Mapping(source = "user.userNumber", target = "userNumber")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "hobby.hobbyNumber", target = "hobbyNumber")
    @Mapping(source = "hobbyReviewTendency", target = "hobbyReviewTendency", qualifiedByName = "intToBoolean")
    @Mapping(source = "hobbyReviewHealthStatus", target = "hobbyReviewHealthStatus", qualifiedByName = "intToBoolean")
    HobbyReviewDTO toHobbyReviewDTO(HobbyReview hobbyReview);

    // int -> boolean 변환기
    @Named("intToBoolean")
    default boolean intToBoolean(int value) {
        return value == 1;  // 1이면 true, 0이면 false
    }
}
