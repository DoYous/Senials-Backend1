package com.senials.common.mapper;

import com.senials.common.entity.Hobby;
import com.senials.hobbyboard.dto.HobbyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HobbyMapper {
    HobbyDTO toHobbyDTO(Hobby hobby);

}
