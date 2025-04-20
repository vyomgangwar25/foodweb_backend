package com.example.demo.controller;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.CSVpractice;
import com.example.demo.repository.CSVRepository;
import com.example.demo.service.CsvService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/CSV")
public class CsvController {
	@Autowired
	private CsvService csvService;

	@Autowired
	private CSVRepository csvRepository;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired

	private Job importCSVJob;

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

	@GetMapping("/download")
	public void downloadCSV(HttpServletResponse response) throws java.io.IOException {
		try {

			response.setContentType("text/csv");
			String fileName = URLEncoder.encode("data.csv", StandardCharsets.UTF_8.toString());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			PrintWriter writer = response.getWriter();

			writer.println("firstname,lastname,college");

//			List<CSVpractice> userDetails = csvRepository.findAll();
			int pageno = 0;
			int pageSize = 10;
			Page<CSVpractice> userDetails = csvRepository.findAll(PageRequest.of(pageno, pageSize));
			while (!userDetails.isEmpty()) {
				for (CSVpractice user : userDetails.getContent()) {
					writer.println((user.getFirstName()) + "," + (user.getLastName()) + "," + (user.getCollege()));
				}
				pageno++;
				System.out.println(pageno);
				userDetails = csvRepository.findAll(PageRequest.of(pageno, pageSize));
			}

			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException("Error while generating CSV file", e);
		}
	}

//	@PostMapping("/upload")
//	public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
//		try {
//			if (file.isEmpty()) {
//				return ResponseEntity.badRequest().body("Empty file");
//			}
//
//			// Save to temp file
//			File tempFile = File.createTempFile("upload-", ".csv");
//			file.transferTo(tempFile);
//			Path path = tempFile.toPath();
//			System.out.println(path);
//
//			 JobParameters params = new JobParametersBuilder()
//		                .addString("filePath",  tempFile.getAbsolutePath())  
//		                .addLong("startAt", System.currentTimeMillis()) 
//		                .toJobParameters();
//
//		        jobLauncher.run(importCSVJob, params);
//
//			return ResponseEntity.ok("Job started successfully.");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(500).body("Job failed: " + e.getMessage());
//		}
//	}

}
