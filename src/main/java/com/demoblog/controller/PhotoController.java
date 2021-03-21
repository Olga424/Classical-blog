package com.demoblog.controller;

import com.demoblog.model.entity.Photo;
import com.demoblog.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload_updateProfileImage(@RequestParam("file")MultipartFile file, Long userId) throws IOException {
        photoService.upload_UpdateProfileImageToUser(file, userId);

        return new ResponseEntity<>("Profile image was successfully uploaded", HttpStatus.OK);
    }

    @PostMapping("{postId}/upload")
    public ResponseEntity<String> uploadImageToPost(@PathVariable("postId") String userId, @PathVariable("postId") String postId, @RequestParam("file") MultipartFile file) throws IOException {
        photoService.uploadImageToPost(file, Long.parseLong(userId), Long.parseLong(postId));

        return new ResponseEntity<>("Image was successfully uploaded", HttpStatus.OK);
    }

    @GetMapping("/profileImage")
    public ResponseEntity<Photo> getProfileImage(Long userId) {
        Photo photo = photoService.getUserProfileImage(userId);

        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    @GetMapping("{postId}/image")
    public ResponseEntity<Photo> getImageToPost(@PathVariable("postId") String postId) {
        Photo photo = photoService.getImageToPost(Long.parseLong(postId));

        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProfilePhoto(Long userId) {
        photoService.deleteProfilePhoto(userId);

        return new ResponseEntity<>("Image was successfully deleted", HttpStatus.OK);
    }

}

