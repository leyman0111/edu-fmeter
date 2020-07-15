package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.fmeter.dao.model.User;
import ru.fmeter.dto.UserDto;
import ru.fmeter.utils.DateTimeUtility;

import java.util.Date;

@Mapper(componentModel = "spring", uses = OrganizationMapper.class)
public interface UserMapper {
    @Mapping(source = "password", target = "pass")
    User userDtoToUser(UserDto userDto);

    @Mapping(source = "pass", target = "password")
    UserDto userToUserDto(User user);

    default String birthdayToDto(Date birthday) {
        return DateTimeUtility.dateToString(birthday);
    }

    default Date dtoToBirthday(String birthday) {
        return DateTimeUtility.stringToDate(birthday);
    }
}
