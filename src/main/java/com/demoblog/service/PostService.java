package com.demoblog.service;

import com.demoblog.model.dto.PostDTO;
import com.demoblog.model.entity.Photo;
import com.demoblog.model.entity.Post;
import com.demoblog.model.entity.User;
import com.demoblog.exception.PostNotFoundException;
import com.demoblog.repository.PhotoRepository;
import com.demoblog.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, PhotoRepository photoRepository, UserService userService) {
        this.postRepository = postRepository;
        this.photoRepository = photoRepository;
        this.userService = userService;
    }

    public Post createPost(PostDTO postDTO, Long userId) {
        User user = userService.getUserById(userId);
        Post post = new Post();

        post.setUser(user);
        post.setContent(postDTO.getContent());
        post.setLikes(0);

        LOG.info("Post for user " + user.getUsername() + "has been created.");
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        LOG.info("Get all posts.");
        return postRepository.findAllByOrderByPostCreatedTimeDesc();
    }

    public Post getPostById(Long userId, Long postId) {
        User user = userService.getUserById(userId);

        LOG.info("get post with id " + postId + " for user " + user.getUsername());
        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    public List<Post> getAllPostsForUser(Long userId) {
        User user = userService.getUserById(userId);
        return postRepository.findAllByUserOrderByPostCreatedTimeDesc(user);
    }

    public Post likePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        Optional<String> likedUsers = post.getLikedUsers()
                .stream()
                .filter(u -> u.equals(username)).findAny();

        if(likedUsers.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }
        return postRepository.save(post);
    }

    public void deletePost(Long postId, Long userId) {
        Post post = getPostById(postId, userId);
        Optional<Photo> photo = photoRepository.findByPostId(postId);

        LOG.info("Post " + postId + " for user " + userId + " has been deleted.");
        postRepository.delete(post);
        photo.ifPresent(photoRepository::delete);
    }

}

