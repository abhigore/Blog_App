package com.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.repisotories.UserRepo;

@Component
public class CustomerUserDetailService  implements org.springframework.security.core.userdetails.UserDetailsService{

	
	@Autowired
	private UserRepo userRepo;
	
	
	
	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
			throws org.springframework.security.core.userdetails.UsernameNotFoundException {
	User user=	this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user", "email:"+username, 0));
		return user;
	}
	
}
