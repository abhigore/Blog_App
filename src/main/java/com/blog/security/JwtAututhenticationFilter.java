package com.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAututhenticationFilter extends OncePerRequestFilter {

	
	@Autowired
	private UserDetailsService  userDetailsService;
	
	
	@Autowired
	private JwtHelper jwtHelper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
//		1.get token 
		
		String requestToken=request.getHeader("Authorization");
		
		// Bearer 2352523sdgsg
		
		
		System.out.println(requestToken); 
		
		String username=null;
		
		String token=null;
		
		
		if(requestToken!=null&& requestToken.startsWith("Bearer")) {
			
			token=requestToken.substring(7);
			
			try {
			username=this.jwtHelper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException e){

				System.out.println("Unable to get jwt token ");
			}
			catch(ExpiredJwtException e) {
				System.out.println("jwt token has expired ");
			}catch (MalformedJwtException e) {
				System.out.println("Invalid jwt ");
			}
		}
		else {
			System.out.println("Jwt token doesnot begin with Bearer ");
		}
		//once we get token we validate token 
		if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails =this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtHelper.validateToken(token, userDetails)) {
				//is eveything going fine 
				// need to do authentication 
				//need to create object of authentication 
				UsernamePasswordAuthenticationToken  usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("Invalid jwt token ");
			}
			
		}
		else {
			System.out.println("Username is null or context is not  null ");
		}
		
		filterChain.doFilter(request, response);
	}

}
