package com.blog.repisotories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
