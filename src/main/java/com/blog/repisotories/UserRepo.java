package com.blog.repisotories;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.User;
import com.blog.payloads.UserDto;

public interface UserRepo  extends JpaRepository<User, Integer>{

	Optional<User>findByEmail(String email);
		
	

}
