package com.ryc.store.mapper;

import com.ryc.store.entity.ProductEntity;
import com.ryc.store.model.ProductModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

	private final ModelMapper modelMapper;

	public ProductMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public ProductModel convertEntityToModel(ProductEntity productEntity){
		ProductModel productModel = modelMapper.map(productEntity, ProductModel.class);
		return productModel;
	}

	public ProductEntity convertModelToEntity(ProductModel productModel){
		ProductEntity productEntity = modelMapper.map(productModel, ProductEntity.class);
		return productEntity;
	}
}
