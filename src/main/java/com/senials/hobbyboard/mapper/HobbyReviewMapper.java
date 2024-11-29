package com.senials.hobbyboard.mapper;

import com.senials.hobbyboard.dto.HobbyReviewDTO;
import com.senials.hobbyboard.entity.HobbyReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HobbyReviewMapper {
    @Mapping(source = "user.userNumber", target = "userNumber")          // User의 userNumber -> DTO의 userNumber
    @Mapping(source = "user.userName", target = "userName")              // User의 userName -> DTO의 userName
    @Mapping(source = "hobby.hobbyNumber", target = "hobbyNumber")      // Hobby의 hobbyNumber -> DTO의 hobbyNumber
    @Mapping(source = "hobbyReviewWriteDate", target = "hobbyReviewWriteDate")
    HobbyReviewDTO toHobbyReviewDTO(HobbyReview hobbyReview);

}