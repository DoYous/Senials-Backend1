package com.senials.partyboards.mapper;

import com.senials.entity.User;
import com.senials.partyboards.dto.UserDTOForPublic;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /* User -> UserDTOForPublic */
    UserDTOForPublic toUserDTOForPublic(User user);
}
