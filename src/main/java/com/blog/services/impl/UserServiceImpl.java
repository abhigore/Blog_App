package com.blog.services.impl;
import com.blog.repisotories.*;
import com.blog.services.*;
import java.util.List;
import java.util.stream.Collectors;
import com.blog.payloads.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.blog.exceptions.*;
import com.blog.payloads.UserDto;
import com.blog.repisotories.UserRepo;
import com.blog.services.User_service;
import com.blog.config.AppConstants;
import com.blog.entities.*;



@Service
public class UserServiceImpl implements User_service {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	Logger logger =LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public UserDto createUser(UserDto userDto) {
		logger.info("enter in the method");
		User user= this.dtoToUser(userDto);
//		System.out.println("enter  0");
		User saveduser = this .userRepo.save(user);
		UserDto userDto1 =this.userToDto(saveduser);
		
		return userDto1;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		
		User updateduser= this.userRepo.save(user);
		UserDto userDto1= this.userToDto(updateduser);
		
				
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User>users=this.userRepo.findAll();
		List<UserDto>userDtos=users.stream().map(user->this.userToDto(user)).collect((Collectors.toList()));
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
		
		this.userRepo.delete(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		User user= this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}
	
	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
				
				
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		
		user.setPassword(this.passwordencoder.encode(user.getPassword()));
		
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser= this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
