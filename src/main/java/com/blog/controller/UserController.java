package com.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.User_service;
import com.blog.services.impl.UserServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.PreRemove;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name="scheme1")
@RequestMapping("/api")


public class UserController {
	
	
	@Autowired
	private  UserServiceImpl userservice ;
	
	@Autowired
	 private PasswordEncoder encoder;
	
	
	
	//POST -create user
	@PostMapping ("/user")
	public ResponseEntity<UserDto>  createuser(@Valid @RequestBody UserDto userDto){
		
				UserDto createUserDto= this.userservice.createUser(userDto);
				String password = encoder.encode(createUserDto.getPassword());
				createUserDto.setPassword(password);
				
				return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
		
	
	//PUT- update user
	@PutMapping("/user/{userId}")
	public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId ){
		
			UserDto updatedUser= this.userservice.updateUser(userDto, userId);
			
		return ResponseEntity.ok(updatedUser);
		
		
	}
	
	//DELETE- delete user
 	@PreAuthorize("hasRole('ADMIN')")
	
	@DeleteMapping("/user/{userId}")
	public  ResponseEntity<ApiResponse> deleteuser ( @PathVariable Integer userId){
		this.userservice.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully ",true),HttpStatus.OK);
	}
	
	//another way to return output for delete method 
//	public  ResponseEntity<String> deleteuser ( @PathVariable Integer userId){
//		this.userservice.deleteUser(userId);
//		return new ResponseEntity("user deleted successfully ",HttpStatus.OK);
//	}
	
	//GET- get user
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userservice.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userservice.getUserById(userId));
	}


	
	
	
	
	

}
