package com.senials.user.mapper;

import com.senials.entity.User;
import com.senials.user.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // User -> MypageDTO
    UserDTO toMypageDTO(User user);

}
