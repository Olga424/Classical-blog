package com.demoblog.model.dto;

import com.demoblog.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
public class UserDTO {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String bio;

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setBio(user.getBiography());
        return userDTO;
    }

}
