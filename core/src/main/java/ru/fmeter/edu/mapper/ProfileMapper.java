package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.fmeter.dao.model.User;
import ru.fmeter.dto.ProfileDto;
import ru.fmeter.utils.DateTimeUtility;

import java.util.Date;

@Mapper(componentModel = "spring", uses = OrganizationMapper.class)
public interface ProfileMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "pass", ignore = true),
            @Mapping(target = "rating", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "blocked", ignore = true),
            @Mapping(target = "active", ignore = true)
    })
    User profileToUser(ProfileDto profile);

    @Mappings({
            @Mapping(target = "pass", ignore = true)
    })
    ProfileDto userToProfile(User user);

    default String birthdayToDto(Date birthday) {
        return DateTimeUtility.dateToString(birthday);
    }

    default Date dtoToBirthday(String birthday) {
        return DateTimeUtility.stringToDate(birthday);
    }
}
