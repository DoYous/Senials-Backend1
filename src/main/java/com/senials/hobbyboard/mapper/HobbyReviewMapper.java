package com.senials.hobbyboard.mapper;

import com.senials.common.entity.HobbyReview;
import com.senials.hobbyboard.dto.HobbyReviewDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HobbyReviewMapper {

    HobbyReviewDTO toHobbyReviewDTO(HobbyReview hobbyReview);

    HobbyReview toHobbyReviewEntity(HobbyReviewDTO hobbyReviewDTO);

}
