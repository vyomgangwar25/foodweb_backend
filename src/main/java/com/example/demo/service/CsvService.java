package com.example.demo.service;

import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.CSVpractice;
import com.example.demo.repository.CSVRepository;
import com.opencsv.CSVReader;

@Service
public class CsvService {

	@Autowired
	private CSVRepository csvRepository;

	private static final List<String> EXPECTED_HEADERS = Arrays.asList("firstname", "lastname", "college");

	public ResponseEntity<String> csvHandler(MultipartFile file) {
		try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {

			// read the first line of csv i.e header
			String[] rawHeaders = reader.readNext();

			List<String> csvHeaders = Arrays.stream(rawHeaders).map(String::trim).filter(header -> !header.isEmpty())
					.collect(Collectors.toList());

			if (csvHeaders == null) {
				return ResponseEntity.badRequest().body("CSV file is empty");
			}

			if (!csvHeaders.containsAll(EXPECTED_HEADERS)) {
				return ResponseEntity.badRequest().body("CSV headers do not match expected headers");
			}

			String rowData[];
			while ((rowData = reader.readNext()) != null) {
				Map<String, String> dataMap = new HashMap<>();
				for (int i = 0; i < 3; i++) {
					dataMap.put(EXPECTED_HEADERS.get(i), rowData[i]);
				}

				CSVpractice data = new CSVpractice();
				data.setFirstName(dataMap.get("firstname"));
				data.setLastName(dataMap.get("lastname"));
				data.setCollege(dataMap.get("college"));

				csvRepository.save(data);
			}

			return ResponseEntity.ok("Data uploaded successfully!");

		} catch (Exception e) {
			return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
		}
	}
}
