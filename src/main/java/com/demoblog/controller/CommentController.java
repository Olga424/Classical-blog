package com.demoblog.controller;


import com.demoblog.model.dto.CommentDTO;
import com.demoblog.model.entity.Comment;
import com.demoblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentDTO commentDTO;

    @Autowired
    public CommentController(CommentService commentService, CommentDTO commentDTO) {
        this.commentService = commentService;
        this.commentDTO = commentDTO;
    }

    @PostMapping("/user/{userId}/{postId}/create")
    public ResponseEntity<Comment> createComment(@PathVariable("postId") String postId, @PathVariable("userId") String userId, @RequestBody CommentDTO commentDTO) {
        Comment createdComment = commentService.addComment(Long.parseLong(userId), Long.parseLong(postId), commentDTO);

        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @GetMapping("/user/{postId}/all")
    public ResponseEntity<List<CommentDTO>> getAllCommentsToPost(@PathVariable("postId") String postId) {
        List<CommentDTO> commentDTOList = commentService.getAllCommentsForPost(Long.parseLong(postId))
                .stream()
                .map(commentDTO::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @PostMapping("/{commentId}/delete")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") String commentId) {
        commentService.deleteComment(Long.parseLong(commentId));

        return new ResponseEntity<>("Post was deleted", HttpStatus.OK);
    }

}
