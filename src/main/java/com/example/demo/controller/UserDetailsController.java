package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserDetail;
import com.example.demo.repository.UserDetailsRepository;

@RestController
@RequestMapping("/userDetail")
public class UserDetailsController {

	@Autowired
	private UserDetailsRepository detailsRepository;

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestBody UserDetail detail) {
		detailsRepository.save(detail);
		return ResponseEntity.ok("Data saved successfully!");
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getData(@PathVariable Integer id) {
		Optional<UserDetail> userDetailOptional = detailsRepository.findById(id);

		if (userDetailOptional.isPresent()) {
			return ResponseEntity.ok(userDetailOptional.get());
		} else {
			return ResponseEntity.ok("No data found for ID: " + id);
		}
	}
}
