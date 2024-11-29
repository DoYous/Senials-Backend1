package com.senials.hobbyboard.mapper;

import com.senials.entity.HobbyReview;
import com.senials.hobbyboard.dto.HobbyReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HobbyReviewMapper {

    HobbyReviewDTO toHobbyReviewDTO(HobbyReview hobbyReview);

}
