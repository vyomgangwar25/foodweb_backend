package com.example.demo.controller;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.CSVConfig;
import com.example.demo.entity.CSVpractice;
import com.example.demo.repository.CSVRepository;
import com.example.demo.service.CsvService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/CSV")
public class CsvController {
	@Autowired
	private CsvService csvService;
	
	@Autowired
	private CSVRepository csvRepository;
	
	@Autowired
	private CSVConfig csvConfig;

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
	
//	 @PostMapping("/upload")
//	    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
//	        try {
//	            if (file.isEmpty()) {
//	                return ResponseEntity.badRequest().body("Empty file");
//	            }
//
//	            // Save to temp file
//	            File tempFile = File.createTempFile("upload-", ".csv");
//	            file.transferTo(tempFile);
//	            Path path = tempFile.toPath();
//
//	            // Launch batch job
//	            Job job = csvConfig.job(new FileSystemResource(path));
//	            JobParameters params = new JobParametersBuilder()
//	                    .addLong("startAt", System.currentTimeMillis())
//	                    .toJobParameters();
//
//	            jobLauncher.run(job, params);
//
//	            return ResponseEntity.ok("Job started successfully.");
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return ResponseEntity.status(500).body("Job failed: " + e.getMessage());
//	        }
//	    }
	//}
	
	@GetMapping("/download")
	 public ResponseEntity<String>downloadCSV()
	 {
		List<CSVpractice>datafromdatabase=csvRepository.findAll();
		return null;
	 }
	

}
