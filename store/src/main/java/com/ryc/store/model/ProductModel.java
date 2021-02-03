package com.ryc.store.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductModel {

	private String name;
	private BigDecimal price;
	private Integer availability;
}
