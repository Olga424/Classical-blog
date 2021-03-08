package com.demoblog.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String bio;

}
