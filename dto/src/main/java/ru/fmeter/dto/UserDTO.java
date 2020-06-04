package ru.fmeter.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDTO {
    private Long id;
    @NonNull
    private String userName;
    @NonNull
    private String password;

    public UserDTO() { }

    public UserDTO(@NonNull String userName, @NonNull String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserDTO(@NonNull Long id, @NonNull String userName, @NonNull String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }
}
