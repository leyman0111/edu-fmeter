package ru.fmeter.dto;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private String midName;
    private LocalDate birthday;
    private String position;
    @NonNull
    private OrganizationDto organization;

    public UserDto() { }
}
