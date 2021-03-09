package com.demoblog.controller;

import com.demoblog.model.dto.PostDTO;
import com.demoblog.model.entity.Post;
import com.demoblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@CrossOrigin
public class PostController {

    private final PostService postService;
    private final PostDTO postDTO;

    @Autowired
    public PostController(PostService postService, PostDTO postDTO) {
        this.postService = postService;
        this.postDTO = postDTO;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Post> createObject(@RequestBody PostDTO postDTO, @PathVariable("userId") String userId) {
        Post post = postService.createPost(postDTO, Long.parseLong(userId));

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postDTOList = postService.getAllPosts()
                .stream()
                .map(postDTO::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(@PathVariable("userId") String userId) {
        List<PostDTO> postDTOList = postService.getAllPostsForUser(Long.parseLong(userId))
                .stream()
                .map(postDTO::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable("postId") String postId, @PathVariable("username") String username) {
        Post post = postService.likePost(Long.parseLong(postId), username);
        PostDTO likedPost = postDTO.convertToDTO(post);

        return new ResponseEntity<>(likedPost, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") String postId, @PathVariable("userId") String userId) {
        postService.deletePost(Long.parseLong(userId), Long.parseLong(postId) );

        return new ResponseEntity<>("Post was deleted", HttpStatus.OK);
    }
}

