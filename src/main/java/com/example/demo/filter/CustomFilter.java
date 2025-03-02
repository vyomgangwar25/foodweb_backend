package com.example.demo.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:3000")
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
		//System.out.println(authHeader);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token_frontend = authHeader.substring(7);
		//	System.out.println(token_frontend);

			if (ObjectUtils.isEmpty(token_frontend) || token_frontend.equals("null")) {
				filterChain.doFilter(request, response);
				return;
			}
			String userEmail = jwtToken.extractUsername(token_frontend);
			//System.out.println(userEmail);
			if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				User existingUser = repository.findByEmail(userEmail);
				User isValid = jwtToken.validateToken(token_frontend, existingUser);
				System.out.println(isValid);
				if (isValid != null) {
					String role = isValid.getRoleId().getRole();
					List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							existingUser, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authToken);

				} else {
					System.out.println("invalid token....");
				}
			}

		}

		System.out.println("Hello from custom filter...");
		filterChain.doFilter(request, response);
	}

}
