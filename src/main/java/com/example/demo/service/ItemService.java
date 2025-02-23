package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddItemDTO;
import com.example.demo.dto.UpdateDataDTO;
import com.example.demo.entity.Items;
import com.example.demo.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public ResponseEntity<String> add(AddItemDTO data) {
		Items item = new Items(data.getName(), data.getPrice(), data.getRating());
		itemRepository.save(item);
		return ResponseEntity.ok("data saved");

	}

	public ResponseEntity<?> list() {
		List<Items> list = itemRepository.findAll();
		return ResponseEntity.ok(list);
	}

	public ResponseEntity<?> search(String text) {
		List<Items> list = itemRepository.findByNameOrRatingContaining(text);
		return ResponseEntity.ok(list);
	}

	public ResponseEntity<?> update(Integer id, UpdateDataDTO data) {
		try {
			Items existingInfo = itemRepository.findById(id).get();
			existingInfo.setName(data.getName());
			existingInfo.setPrice(data.getPrice());
			itemRepository.save(existingInfo);
			return ResponseEntity.ok("data updated successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage() + "data not found"); 
		}
	}

}
