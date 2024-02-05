package com.blog.services.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repisotories.CategoryRepo;
import com.blog.repisotories.PostRepo;
import com.blog.repisotories.UserRepo;
import com.blog.services.PostService;
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postrepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	//create post 
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		
		Category  category =this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException((String) "Category", "Category Id", categoryId));
				
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImage("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost=this.postrepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}
	//update post

	@Override
	public PostDto updatepost(PostDto postdto, Integer postId) {
			Post gotpost=this.postrepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
			gotpost.setTitle(postdto.getTitle());
			gotpost.setContent(postdto.getContent());
			gotpost.setImage(postdto.getImage());
			Post savedpost=this.postrepo.save(gotpost);
			PostDto updatedpost=this.modelMapper.map(savedpost, PostDto.class);
			
		return updatedpost;
	}
	//delete post

	@Override
	public void deletepost(Integer postId) {
		Post gotpost=this.postrepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		this.postrepo.delete(gotpost);

		
	}
	
	//getpost
	
	@Override 
	public PostResponse getAllPost(Integer PageNumber,Integer  PageSize  ,String sortBy,String sortDirection) {
		Sort sort=null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
			
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		
		PageRequest p=PageRequest.of(PageNumber,PageSize, sort);
		Page<Post>pagepost=this.postrepo.findAll(p);
		List<Post>allpost=pagepost.getContent();
		
		List<PostDto> postDtos=allpost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

			PostResponse postResponse= new PostResponse();
			postResponse.setContent(postDtos);
			postResponse.setPageNumber(pagepost.getNumber());
			postResponse.setPageSize(pagepost.getSize());
			postResponse.setTotaleElements(pagepost.getTotalElements());
			postResponse.setTotalPage(pagepost.getTotalPages());
			postResponse.setLastPage(pagepost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		 Post gotPost=this.postrepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId", postId));
		 PostDto gotPostDto=this.modelMapper.map(gotPost, PostDto.class);
		return gotPostDto;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts=this.postrepo.findByCategory(cat);
		List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId",userId));
		List<Post> posts=this.postrepo.findByUser(user);
	
//		List<PostDto> postDtos =new ArrayList<>();
//		
//		for( Post post :posts)
//		{
//			PostDto postDto = this.modelMapper.map(post ,PostDto.class);
//			postDtos.add(postDto);
//		}
		List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());


		return postDtos;
	}
	
	//search post
	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post>posts=this.postrepo.findByTitleContaining(keyword);
		List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	
	

}
