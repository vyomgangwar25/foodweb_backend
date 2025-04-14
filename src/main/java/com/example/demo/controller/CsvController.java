package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CsvService;

@RestController
@RequestMapping("/CSV")
public class CsvController {
	@Autowired
	private CsvService csvService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadCSV(@RequestParam("file") MultipartFile file) {
		if (file == null || file.isEmpty()) {
			System.out.println("File not found or is empty");
			return ResponseEntity.badRequest().body("File is missing or empty");
		}
		final String fileType = "text/csv";
		System.out.println(file.getContentType());
		if (!fileType.equals(file.getContentType())) {
			return ResponseEntity.status(422).body("file type is not csv");
		}
		return csvService.csvHandler(file);
	}

}
