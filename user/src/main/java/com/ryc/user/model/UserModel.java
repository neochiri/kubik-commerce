package com.ryc.user.model;

import com.ryc.user.validation.EnumeratorValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
public class UserModel {

	@NotBlank
	private String email;
	@NotBlank
	private String name;
	@EnumeratorValidator(enumClass = UserRole.class)
	private UserRole role;
}
