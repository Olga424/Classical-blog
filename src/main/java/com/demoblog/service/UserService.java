package com.demoblog.service;

import com.demoblog.authentication.Signup;
import com.demoblog.exception.UserAlreadyExistsException;
import com.demoblog.exception.UserNotFoundException;
import com.demoblog.model.dto.UserDTO;
import com.demoblog.model.entity.User;
import com.demoblog.model.enums.Roles;
import com.demoblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    //private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository /*BCryptPasswordEncoder passwordEncoder*/ ) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    public User create(Signup signup) {
        User user = new User();

        user.setEmail(signup.getEmail());
        user.setName(signup.getFirstname());
        user.setLastname(signup.getLastname());
        user.setUsername(signup.getUsername());
        user.setPassword(/*passwordEncoder.encode(*/signup.getPassword());
        return user;
    }

    public User createUser(Signup signup) {
        User user = create(signup);
        user.setRole(Roles.USER);

        try {
            LOG.info("New user with username " + user.getUsername() + " has been registered.");
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error while register new user with username " + user.getUsername() + " occurred.", e.getMessage());
            throw new UserAlreadyExistsException("The user with username " + user.getUsername() + " already exists.");
        }
    }

    public User createAdmin(Signup signup) {
        User admin = create(signup);
        admin.setRole(Roles.ADMIN);

        try {
            LOG.info("New admin with username " + admin.getUsername() + " has been registered.");
            return userRepository.save(admin);
        } catch (Exception e) {
            LOG.error("Error while register new admin with username " + admin.getUsername() + " occurred.", e.getMessage());
            throw new UserAlreadyExistsException("The user with username " + admin.getUsername() + " already exists.");
        }
    }

    public User updateUser(UserDTO userDTO, Long userId) {
        User user = getUserById(userId);

        user.setName(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setBiography(userDTO.getBio());

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
