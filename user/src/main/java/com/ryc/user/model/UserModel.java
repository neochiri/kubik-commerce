package com.ryc.user.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class UserModel {

	@NotEmpty
	private String email;
	@NotEmpty
	private String name;
	@NotEmpty
	private String role;
}
