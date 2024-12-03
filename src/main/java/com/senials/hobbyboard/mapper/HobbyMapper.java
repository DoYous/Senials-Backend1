package com.senials.hobbyboard.mapper;

import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.common.entity.Hobby;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HobbyMapper {
    HobbyDTO toHobbyDTO(Hobby hobby);

}
