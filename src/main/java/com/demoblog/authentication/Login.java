package com.demoblog.authentication;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class Login {

    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Password is required")
    private String password;

}
