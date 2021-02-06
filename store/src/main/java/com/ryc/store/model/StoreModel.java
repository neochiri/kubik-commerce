package com.ryc.store.model;

import lombok.Data;


@Data
public class StoreModel {

	private String name;
	private AddressModel address;
	private String category;
	private String owner;
}
