package com.ryc.user.model;

public enum UserRole {

	ADMIN("ADMIN"),
	USER("USER");

	private String value;

	UserRole(String value) {
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}
}
