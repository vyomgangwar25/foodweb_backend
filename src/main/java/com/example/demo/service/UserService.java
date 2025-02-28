package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegistrationDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity<?> Login(LoginDTO data) {
		try {
			User user = repository.findByEmail(data.getEmail());
			if (user != null) {
				if (passwordEncoder.matches(data.getPassword(), user.getPassword())) {
					return ResponseEntity.ok(user);
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect old password!!");
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
		}

	}

	public ResponseEntity<?> Registration(RegistrationDTO data) {
		try {
			User user = repository.findByEmail(data.getEmail());
			if (user == null) {

				User newUser = new User(data.getName(), data.getEmail(),
						passwordEncoder.encode(String.valueOf(data.getPassword())),
						rolesRepository.findById(data.getRoleId()).get());
				repository.save(newUser);
				return ResponseEntity.ok("User registered Successfully!!");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user already exist!!");
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("dont know");
		}
	}

}
