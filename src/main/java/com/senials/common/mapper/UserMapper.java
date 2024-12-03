package com.senials.common.mapper;

import com.senials.common.entity.User;
import com.senials.user.dto.UserDTOForPublic;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /* User -> UserDTOForPublic */
    UserDTOForPublic toUserDTOForPublic(User user);
}
