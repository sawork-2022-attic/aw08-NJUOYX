package com.micropos.poscounter.mapper;


import com.micropos.poscounter.dto.UserDto;
import com.micropos.poscounter.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
}
