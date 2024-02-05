package com.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.entities.Category;
import com.blog.entities.Comment;
import com.blog.entities.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	
	private Integer postId;
	@NotBlank 
	@Size(min=4)
	private String title;
	
	@NotBlank
	@Size(min=10)
	private String content;
	
	
	private String image;
	
	
	private Date addedDate;
	
	
	private CategoryDto category;
	
	
	private UserDto user;
	
	
	private Set<CommentDto>comments=new HashSet<>();

}
