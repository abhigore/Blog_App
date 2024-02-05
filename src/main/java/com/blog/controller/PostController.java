package com.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StreamUtils;

import com.blog.config.AppConstants;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repisotories.*;
import com.blog.services.PostService;
import com.blog.services.impl.FileServiceImpl;
import com.blog.services.impl.PostServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@SecurityRequirement(name="scheme1")
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileServiceImpl fileServiceImpl;
	
	@Value("${custom.file.location}")
	private String path;
	
	
	
	//create
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postdto,@PathVariable Integer userId ,@PathVariable Integer categoryId){
		PostDto createPost= this.postService.createPost(postdto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
		
	}
	
	
	
	//get by user

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
				List<PostDto> posts=this.postService.getPostByUser(userId);
				
				
		
		return  new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}

	
	//get by Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
				List<PostDto>posts=this.postService.getPostByCategory(categoryId);
				
				return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	
	
	
	//get by postId
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postdto=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postdto
				,HttpStatus.OK);
	}
	
	// get all post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse>getAllPost(
			@RequestParam (value="pageNumber ",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam (value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value="sortDirection",defaultValue =AppConstants.SORT_DIRECTION,required=false)String sortDirection
			){
		PostResponse postresponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDirection);
		return new ResponseEntity<PostResponse>(postresponse,HttpStatus.OK);
	}
	
	//update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postdto,@PathVariable Integer postId){
	PostDto updatedpostDto= this.postService.updatepost(postdto, postId);
	return  ResponseEntity.ok(updatedpostDto);
	}
	
	//delete post 
	@DeleteMapping("/posts/{postId}")
		public ApiResponse deletePost(@PathVariable Integer postId) {
			this.postService.deletepost(postId);
			return new ApiResponse("Post has been deleted successfully ",true);
			
			
		}
	
	//search post
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>>searchPost(@PathVariable("keyword") String keyword){
			List<PostDto>searchedposts=this.postService.searchPosts(keyword);
			return new  ResponseEntity<List<PostDto>>(searchedposts,HttpStatus.OK);
	}
	
	@PostMapping("post/image/upload/{postId}")
	public ResponseEntity<PostDto> FileUpload(@RequestParam("file") MultipartFile file,@PathVariable Integer postId)throws Exception
	{	
		PostDto postdto=this.postService.getPostById(postId);
		String fileName=this.fileServiceImpl.uploadImage(path, file);
		
		postdto.setImage(fileName);
		PostDto updatedDto=this.postService.updatepost(postdto, postId);
		return new ResponseEntity<PostDto>(updatedDto,HttpStatus.OK);
	}
	
	@GetMapping(value="post/image/{imageName}",produces =MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable ("imageName")String imageName,HttpServletResponse response)throws IOException{
		InputStream resource =this.fileServiceImpl.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
}