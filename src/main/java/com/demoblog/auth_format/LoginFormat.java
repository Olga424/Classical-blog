package com.demoblog.auth_format;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginFormat {

    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Password is required")
    private String password;

}
