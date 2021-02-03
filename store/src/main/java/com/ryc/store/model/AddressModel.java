package com.ryc.store.model;

import lombok.Data;

@Data
public class AddressModel {

	private String street;
	private String city;
	private double coordinateX;
	private double coordinateY;
}
