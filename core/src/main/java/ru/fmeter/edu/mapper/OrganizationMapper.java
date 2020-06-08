package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import ru.fmeter.dao.model.Organization;
import ru.fmeter.dto.OrganizationDto;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    Organization orgDtoToOrg(OrganizationDto organizationDto);
}
