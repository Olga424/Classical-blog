package com.demoblog.service;

import com.demoblog.exception.UserAlreadyExistsException;
import com.demoblog.exception.UserNotFoundException;
import com.demoblog.model.dto.UserDTO;
import com.demoblog.model.entity.User;
import com.demoblog.request.SignUpRequest;
import com.demoblog.model.enums.Roles;
import com.demoblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        User user = new User();

        user.setEmail(.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(Roles.USER);

        try {
            LOG.info("User with username " + user.getUsername() + " has been registered.");
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error while register user with username " + user.getUsername() + " occurred.", e.getMessage());
            throw new UserAlreadyExistsException("The user with username " + user.getUsername() + " already exists.");
        }
    }

    public User updateUser(UserDTO userDTO, Long userId) {
        User user = getUserById(userId);

        user.setName(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());

        try {
            LOG.info("User with username " + user.getUsername() + " has been registered.");
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error while update user with username " + user.getUsername() + " occurred.", e.getMessage());
            throw new UserNotFoundException("The user with username " + user.getUsername() + " not found.");
        }
    }

    public User getUserById(Long userId) {
        LOG.info("Get user with id " + userId + " by id.");

        return userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
    }

    public void deleteUser(Long userId) {
        LOG.info("Delete user with id " + userId);
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        userRepository.delete(user);
    }

}

