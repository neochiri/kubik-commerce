package com.ryc.store.service.iface;

import com.ryc.store.model.ProductModel;

import java.util.List;

public interface ProductService {

	List<ProductModel> findAllProductsByStore(String storeName);
	ProductModel findProductByStore(String storeName);
	ProductModel createProduct(String storeName, ProductModel productModelToCreate);
	ProductModel updateProduct(String storeName, ProductModel productModelToUpdate);
	void deleteProduct(String storeName, String productName);
}
