package com.demoblog.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private Long userId;
    @Column(columnDefinition = "text", nullable = false)
    private String message;
    @Column
    private LocalDateTime commentCreatedTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    protected void createComment() {
        this.commentCreatedTime = LocalDateTime.now();
    }
}

