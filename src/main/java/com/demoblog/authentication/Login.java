package com.demoblog.authentication;

import javax.validation.constraints.NotEmpty;

public class Login {

    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Password is required")
    private String password;

}
