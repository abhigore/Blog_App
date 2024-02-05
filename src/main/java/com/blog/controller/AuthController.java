package com.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.security.JwtHelper;
import com.blog.services.User_service;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@SecurityRequirement(name="scheme1")
@RequestMapping("/api/v1/auth/")
public class AuthController {

	
	@Autowired
	private JwtHelper jwthelper;
	
	
	@Autowired
	private UserDetailsService userdetailsService;
	
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private User_service user_service;
	
	
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse>createToken(
		@RequestBody JwtAuthRequest request
		) throws Exception{
		
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails=this.userdetailsService.loadUserByUsername(request.getUsername());
		
		String token=this.jwthelper.generateToken(userDetails);
		
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
	}
	
	private void authenticate(String username,String password) throws Exception {
		
		try {
		UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, password);
		
		this.authenticationManager.authenticate(authenticationToken);
		}
		catch(BadCredentialsException e) {
			
			 throw new BadCredentialsException("invalid username !!");
		}
	}
	
	//register new user api
	
	
	@PostMapping("/register")
	public ResponseEntity<UserDto>registerUser(@RequestBody UserDto userDto){
		
		UserDto registerdUser= this.user_service.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registerdUser,HttpStatus.CREATED);
	}
	
}
