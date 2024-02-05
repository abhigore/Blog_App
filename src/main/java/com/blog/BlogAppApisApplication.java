package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repisotories.RoleRepo;

@SpringBootApplication
@Configuration
public class BlogAppApisApplication implements CommandLineRunner{

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private RoleRepo roleRepo ;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("password = "+this.passwordEncoder.encode("abhishek"));
		try {
			
			Role role1=new Role();
			role1.setId( AppConstants.ADMIN_USER);
			role1.setName("ADMIN_USER");
			
			Role role2= new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("NORMAL_USER");
			
			List<Role>roles=List.of(role1,role2);
			
			List<Role> result=this.roleRepo.saveAll( roles);
			
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	};

}
