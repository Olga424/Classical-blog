package com.demoblog.service;

import com.demoblog.exception.PostNotFoundException;
import com.demoblog.exception.UserNotFoundException;
import com.demoblog.model.dto.CommentDTO;
import com.demoblog.model.entity.Comment;
import com.demoblog.model.entity.Post;
import com.demoblog.model.entity.User;
import com.demoblog.repository.CommentRepository;
import com.demoblog.repository.PostRepository;
import com.demoblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment addComment(Long userId, Long postId, CommentDTO commentDTO) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setPost(post);
        comment.setMessage(commentDTO.getMessage());

        LOG.info("Add comment for post with id " + post.getId());
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        LOG.info("Get all comments for post with id " + post.getId());
        return commentRepository.findAllByPost(post);
    }

    public void deleteComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        LOG.info("Delete comment with id " + commentId);
        comment.ifPresent(commentRepository::delete);
    }

}
