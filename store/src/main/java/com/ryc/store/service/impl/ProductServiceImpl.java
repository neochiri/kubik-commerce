package com.ryc.store.service.impl;

import com.ryc.store.model.ProductModel;
import com.ryc.store.service.iface.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
	@Override
	public List<ProductModel> findAllProductsByStore(String storeName) {
		return null;
	}

	@Override
	public ProductModel findProductByStore(String storeName) {
		return null;
	}

	@Override
	public ProductModel createProduct(String storeName, ProductModel productModelToCreate) {
		return null;
	}

	@Override
	public ProductModel updateProduct(String storeName, ProductModel productModelToUpdate) {
		return null;
	}

	@Override
	public void deleteProduct(String storeName, String productName) {

	}
}
