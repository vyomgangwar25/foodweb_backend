package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CSVpractice;
import java.util.List;


@Repository
public interface CSVRepository extends JpaRepository<CSVpractice, Integer> {
	 List<CSVpractice> findByLastName(String lastName);

}
