package com.ryc.store.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "store")
@Data
public class StoreEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(unique = true)
	private String name;
	private String street;
	private String city;
	private double coordinateX;
	private double coordinateY;
	private String category;
	@OneToMany(mappedBy = "store")
	private List<ProductEntity> products;
}
