package com.blog.services;
import com.blog.entities.User;
import com.blog.payloads.UserDto;
import java.util.*;
public interface User_service  {
	
	UserDto registerNewUser(UserDto user);

	UserDto createUser(UserDto user);
	
	
	UserDto updateUser(UserDto user,Integer userId);
	
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	
	
}
