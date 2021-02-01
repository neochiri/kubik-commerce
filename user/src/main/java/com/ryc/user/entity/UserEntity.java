package com.ryc.user.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String role;
}
