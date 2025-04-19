package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserDetail extends CommonEntity {

	private String name;
	private String college;

	@OneToMany(mappedBy = "details")
	@JsonManagedReference
	private List<OrderDetails> orderDetails;

}
