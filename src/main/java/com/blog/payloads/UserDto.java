package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.misc.NotNull;

import com.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	
	@NotEmpty
	@Size(min=4,message = "UserName must be of min 4 characters")
	private String name;
	
	@Email(message = "Your given emailaddress isnot valid ,Please enter valid emailaddress")
	private String email;
	
	@NotEmpty(message = "Password shouldnot be empty")
	@Size(min = 3,max = 10,message = "Password  must be in between 3-10 character length  ")
	private String password;
	
	@NotEmpty(message = "About section should not be empty0")
	private String about ;
	
	
	private Set<RoleDto>roles= new HashSet<>();

}
