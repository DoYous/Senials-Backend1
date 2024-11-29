package com.senials.example.mapper;

import com.senials.example.dto.UserDTO;
import com.senials.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // User -> UserDTO
    UserDTO toUserDTO(User user);

}
