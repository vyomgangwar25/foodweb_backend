package com.example.demo.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CSVpractice extends CommonEntity {
	private String firstName;
	private String lastName;
	private String college;

}
