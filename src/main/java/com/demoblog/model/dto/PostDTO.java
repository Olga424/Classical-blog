package com.demoblog.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class PostDTO {

    private String username;
    private Long id;
    private String title;
    private String caption;
    private String location;
    private Integer likes;
    private Set<String> likedUsers;

}
