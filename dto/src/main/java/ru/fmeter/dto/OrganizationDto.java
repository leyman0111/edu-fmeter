package ru.fmeter.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class OrganizationDto {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String city;
    private String address;
    private String unp;
    private String okpo;

    public OrganizationDto() { }
}
