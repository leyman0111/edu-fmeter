package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.fmeter.dao.model.User;
import ru.fmeter.dto.UserDto;

@Mapper(componentModel = "spring", uses = OrganizationMapper.class)
public interface UserMapper {
    @Mapping(source = "password", target = "pass")
    User userDtoToUser(UserDto userDto);

    @Mapping(source = "pass", target = "password")
    UserDto userToUserDto(User user);
}
