package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.fmeter.dao.model.User;
import ru.fmeter.dto.UserDto;
import ru.fmeter.utils.DateTimeUtility;

import java.util.Date;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class, CertificateMapper.class})
public interface UserMapper {
    @Mapping(target = "pass", ignore = true)
    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    default String birthdayToDto(Date birthday) {
        return DateTimeUtility.dateToString(birthday);
    }

    default Date dtoToBirthday(String birthday) {
        return DateTimeUtility.stringToDate(birthday);
    }
}
