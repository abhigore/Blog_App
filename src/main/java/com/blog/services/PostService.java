package com.blog.services;

import java.util.List;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postdto,Integer userId,Integer  categoryId);
	
	
	
	//update
	PostDto updatepost(PostDto postdto,Integer postId);
	
	//delete 
	void deletepost(Integer postId);
	
	//get all post
	
	PostResponse getAllPost(Integer PageNumber,Integer PageSize,String sortBy,String sortDirection);
	
	//get single post 
	PostDto getPostById(Integer postId);
	
	//get all post by category
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//get  all post by user
	
	List<PostDto> getPostByUser(Integer userId);
	
	
	//search posts 
	List<PostDto>searchPosts(String keyword);
}
