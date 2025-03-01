package com.example.demo.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtToken jwtToken;
	
	@Autowired
	private UserRepository repository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			   
			  Boolean isValid= jwtToken.validateToken(token);
			System.out.println(token);
			System.out.println(isValid);
		}

		System.out.println("Hello from custom filter...");
		filterChain.doFilter(request, response);
	}

}
