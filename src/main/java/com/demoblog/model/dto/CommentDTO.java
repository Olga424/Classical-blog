package com.demoblog.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDTO {

    private Long id;
    private String message;
    private String username;

}
