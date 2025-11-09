package com.forge.Hiyori.dtos;

import lombok.Data;

@Data
public class UserDto {

    private String firstName;
    private String lastName;

    private String profile;

    private String email;
    private String password;

    private boolean isAdmin;
}
