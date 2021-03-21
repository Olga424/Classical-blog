package com.demoblog.service;

import com.demoblog.model.entity.Photo;
import com.demoblog.model.entity.Post;
import com.demoblog.model.entity.User;
import com.demoblog.repository.PhotoRepository;
import com.demoblog.repository.PostRepository;
import com.demoblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class PhotoService {
    public static final Logger LOG = LoggerFactory.getLogger(PhotoService.class);

    private final PhotoRepository photoRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, UserService userService, PostService postService) {
        this.photoRepository = photoRepository;
        this.userService = userService;
        this.postService = postService;
    }

    /* upload new photo or update */
    public void upload_UpdateProfileImageToUser(MultipartFile file, Long userId) throws IOException {
        User user = userService.getUserById(userId);
        LOG.info("Upload profile image for user" + user.getUsername());

        Photo profilePhoto = photoRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(profilePhoto)) {
            photoRepository.delete(profilePhoto);
        }

        Photo photo = new Photo();
        photo.setUserId(user.getId());
        photo.setImageBytes(compressBytes(file.getBytes()));
        photo.setName(file.getOriginalFilename());
        photoRepository.save(photo);
    }

    public Photo getUserProfileImage(Long userId) {
        User user = userService.getUserById(userId);

        LOG.info("Get profile image for user" + user.getUsername());
        Photo photo = photoRepository.findByUserId(user.getId()).orElseThrow(null);
        if (!ObjectUtils.isEmpty(photo)) {
            photo.setImageBytes(decompressBytes(photo.getImageBytes()));
        }
        return photo;
    }

    public void deleteProfilePhoto(Long userId) {
        User user = userService.getUserById(userId);
        Photo photo = photoRepository.findByUserId(user.getId()).orElseThrow(null);

        LOG.info("Delete profile image for user" + user.getUsername());
        photoRepository.delete(photo);
    }

    public Photo uploadImageToPost(MultipartFile file, Long userId, Long postId) throws IOException {
        Post post = postService.getPostById(userId, postId);

        Photo photo = new Photo();
        photo.setPostId(post.getId());
        photo.setImageBytes(compressBytes(file.getBytes()));
        photo.setName(file.getOriginalFilename());

        LOG.info("Upload image to post");
        return photoRepository.save(photo);
    }

    public Photo getImageToPost(Long postId) {
        Photo photo = photoRepository.findByPostId(postId).orElseThrow(null);

        if (!ObjectUtils.isEmpty(photo)) {
            photo.setImageBytes(decompressBytes(photo.getImageBytes()));
        }
        return photo;
    }

    /* compression of image before putting to DB */
    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            LOG.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    /* decompression of image before uploading to page */
    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            LOG.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }

}

