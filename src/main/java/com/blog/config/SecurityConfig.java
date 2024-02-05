package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.CustomerUserDetailService;
import com.blog.security.JwtAuthenticationEntryPoint;
import com.blog.security.JwtAututhenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)

public class SecurityConfig {
	public static final String [] PUBLIC_URLS= {
			"/api/v1/auth/**"	,
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/",
			"/swagger-ui/**",
			"/webjars/**"
	};

	@Autowired 
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAututhenticationFilter jwtAuthenticationFilter;

	@Autowired
	private CustomerUserDetailService customerUserDetailService;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		   
	        http.
	        	csrf( (crsf) ->crsf.disable()).
	        	authorizeHttpRequests((auth)->auth.requestMatchers(HttpMethod.DELETE,"/api/user/*").hasRole("ADMIN"))
	        	
	        	.authorizeHttpRequests((auth)->auth.requestMatchers("/api/v1/auth/**").permitAll())
	        	.authorizeHttpRequests((auth)->auth.requestMatchers("/v3/api-docs").permitAll())
	        	.authorizeHttpRequests((auth)->auth.requestMatchers(HttpMethod.GET).permitAll().anyRequest().authenticated())
	        	.exceptionHandling((excep)->excep.authenticationEntryPoint(jwtAuthenticationEntryPoint))
	        	.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	        
	        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        http.authenticationProvider(daoAuthenticationProvider());
	        DefaultSecurityFilterChain defaultSecurityFilterChain=http.build();
	        
	        return defaultSecurityFilterChain;
	    }

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.customerUserDetailService);
		daoAuthenticationProvider.setPasswordEncoder(encoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
