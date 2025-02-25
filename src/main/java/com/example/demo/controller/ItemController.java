package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AddItemDTO;
import com.example.demo.dto.UpdateDataDTO;
import com.example.demo.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@PostMapping("/add")
	ResponseEntity<?> additems(@RequestBody AddItemDTO data) {
		return itemService.add(data);
	}

	@GetMapping("/list")
	ResponseEntity<?> list() {
		return itemService.list();
	}

	@PutMapping("/update/{id}")
	ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UpdateDataDTO data) {
		return itemService.update(id, data);
	}
	
	@DeleteMapping("/delete/{id}")
	ResponseEntity<?>delete(@PathVariable Integer id)
	{
		return itemService.delete(id);
	}

	@GetMapping("/search")
	ResponseEntity<?> search(@RequestParam String text) {
		return itemService.search(text);
	}

}
