package com.demoblog.controller;

import com.demoblog.model.dto.UserDTO;
import com.demoblog.model.entity.User;
import com.demoblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") String userId) {
        UserDTO userDTO = new UserDTO();
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO currentUser = userDTO.convertToDTO(user);

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @PostMapping("/{userId}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") String userId, @RequestBody UserDTO userDTO) {

        User user = userService.updateUser(userDTO, Long.parseLong(userId));
        UserDTO updatedUser = userDTO.convertToDTO(user);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(Long.parseLong(userId));

        return new ResponseEntity<>("User was successfully deleted", HttpStatus.OK);
    }

}
