package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetails extends CommonEntity {
	private Integer orderValue;
	private String orderName;
	@ManyToOne
	@JsonBackReference 
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserDetail details;

}
