package ru.fmeter.edu.mapper;

import org.mapstruct.Mapper;
import ru.fmeter.dao.model.Certificate;
import ru.fmeter.dto.CertificateDto;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    Certificate dtoToCertificate(CertificateDto certificateDto);

    CertificateDto certificateToDto(Certificate certificate);
}
