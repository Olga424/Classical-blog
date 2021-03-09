package com.demoblog.model.dto;

import com.demoblog.model.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter @Setter
@Component
public class PostDTO {

    private String username;
    private Long id;
    private String content;
    private Integer likes;
    private Set<String> likedUsers;

    public PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();

        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setLikes(post.getLikes());
        postDTO.setLikedUsers(post.getLikedUsers());
        return postDTO;
    }

}
