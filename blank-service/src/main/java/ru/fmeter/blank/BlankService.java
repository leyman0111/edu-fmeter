package ru.fmeter.blank;

import ru.fmeter.dto.CertificateDto;

import java.io.File;

public interface BlankService {
    File generateCertificate(CertificateDto certificate);
}
