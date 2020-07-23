package ru.fmeter.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProfileDto {
    private Long id;
    private String login;
    private String pass;
    private String email;
    private String firstName;
    private String lastName;
    private String midName;
    private String birthday;
    private String position;
    private String country;
    private String local;
    private List<CertificateDto> certificates;
    private OrganizationDto organization;

    public ProfileDto() { }
}
