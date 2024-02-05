package com.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.payloads.CommentDto;
import com.blog.repisotories.CommentRepo;
import com.blog.repisotories.PostRepo;
import com.blog.services.CommentService;
import com.blog.entities.*;
import com.blog.exceptions.ResourceNotFoundException;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	//creating comment 
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedcomment=this.commentRepo.save(comment);
		return this.modelMapper.map(savedcomment, CommentDto.class);
	}

	
	//deleting comment 
	
	@Override
	public void deleteComment(Integer commentId) {
	Comment com=this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
		this.commentRepo.delete(com);
	}

}
