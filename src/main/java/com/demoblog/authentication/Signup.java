package com.demoblog.authentication;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class Signup {

    @Email(message = "The field has to have an email format.")
    @NotBlank(message = "Email is required.")
    private String email;
    @NotEmpty(message = "Name is required.")
    private String firstname;
    @NotEmpty(message = "Lastname is required.")
    private String lastname;
    @NotEmpty(message = "Username is required.")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;

}
