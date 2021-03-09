package com.demoblog.repository;

import com.demoblog.model.entity.Post;
import com.demoblog.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserOrderByPostCreatedTimeDesc(User user);
    List<Post> findAllByOrderByPostCreatedTimeDesc();
    Optional<Post> findPostByIdAndUser(Long id, User user);

}
