package ru.fmeter.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

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
    private String birthday;
    private String position;
    private String country;
    /**
     ISO 639-1:2002:
     * en - английский,
     * es - испанский,
     * ru - русский
     */
    private String local;
    private Integer rating;
    private OrganizationDto organization;
    private boolean blocked = true;

    public UserDto() { }
}
