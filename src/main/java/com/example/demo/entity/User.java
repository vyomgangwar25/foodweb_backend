package com.example.demo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class User extends CommonEntity {
	private String name;
	private String email;
	private Integer password;
	@OneToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Roles roleId;

}
