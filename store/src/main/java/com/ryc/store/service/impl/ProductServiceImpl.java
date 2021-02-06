package com.ryc.store.service.impl;

import com.ryc.store.entity.ProductEntity;
import com.ryc.store.entity.StoreEntity;
import com.ryc.store.exception.BusinessServiceException;
import com.ryc.store.exception.ErrorType;
import com.ryc.store.mapper.ProductMapper;
import com.ryc.store.model.ProductModel;
import com.ryc.store.repository.ProductRepository;
import com.ryc.store.repository.StoreRepository;
import com.ryc.store.service.iface.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final StoreRepository storeRepository;
	private final ProductMapper productMapper;

	public ProductServiceImpl(ProductRepository productRepository, StoreRepository storeRepository, ProductMapper productMapper) {
		this.productRepository = productRepository;
		this.storeRepository = storeRepository;
		this.productMapper = productMapper;
	}

	@Override
	public List<ProductModel> findAllProductsByStore(String storeName) {
		StoreEntity storeEntityFound = findStoreByName(storeName);
		if(Objects.isNull(storeEntityFound)) throw new BusinessServiceException("This store does not exist", ErrorType.BUSINESS_ERROR);

		List<ProductModel> productModelsFound = storeEntityFound.getProducts().stream().map(product -> productMapper.convertEntityToModel(product)).collect(Collectors.toList());
		return productModelsFound;
	}

	@Override
	public ProductModel findProductByStore(String storeName, String productName) {
		StoreEntity storeEntityFound = findStoreByName(storeName);
		if(Objects.isNull(storeEntityFound)) throw new BusinessServiceException("This store does not exist", ErrorType.BUSINESS_ERROR);

		ProductEntity productEntityFound = findProductByName(productName);
		if(Objects.isNull(productEntityFound)) throw new BusinessServiceException("This product does not exist", ErrorType.BUSINESS_ERROR);

		ProductModel productModelFound = productMapper.convertEntityToModel(productEntityFound);
		return productModelFound;
	}

	@Override
	public ProductModel createProduct(String storeName, ProductModel productModelToCreate) {
		if(Objects.isNull(findStoreByName(storeName))) throw new BusinessServiceException("This store does not exist", ErrorType.BUSINESS_ERROR);
		if(Objects.nonNull(findProductByName(productModelToCreate.getName()))) throw new BusinessServiceException("This product already exist", ErrorType.BUSINESS_ERROR);
		ProductEntity productEntityToCreate = productMapper.convertModelToEntity(productModelToCreate);
		ProductEntity productEntityCreated = productRepository.save(productEntityToCreate);
		ProductModel productModelCreated = productMapper.convertEntityToModel(productEntityCreated);
		return productModelCreated;
	}

	@Override
	public ProductModel updateProduct(String storeName, ProductModel productModelToUpdate) {
		StoreEntity storeEntityFound = findStoreByName(storeName);
		if(Objects.isNull(storeEntityFound)) throw new BusinessServiceException("This store does not exist", ErrorType.BUSINESS_ERROR);

		ProductEntity productEntityFound = findProductByName(productModelToUpdate.getName());
		if(Objects.isNull(productEntityFound)) throw new BusinessServiceException("This product does not exist", ErrorType.BUSINESS_ERROR);

		ProductEntity productEntityToUpdate = productMapper.convertModelToEntity(productModelToUpdate);
		productEntityToUpdate.setId(productEntityFound.getId());
		productEntityToUpdate.setStore(storeEntityFound);

		ProductEntity productEntityUpdated = productRepository.save(productEntityToUpdate);
		ProductModel productModelUpdated = productMapper.convertEntityToModel(productEntityUpdated);

		return productModelUpdated;
	}

	@Override
	public void deleteProduct(String storeName, String productName) {
		if(Objects.isNull(findStoreByName(storeName))) throw new BusinessServiceException("This store does not exist", ErrorType.BUSINESS_ERROR);

		ProductEntity productEntityFound = findProductByName(productName);
		if(Objects.isNull(productEntityFound)) throw new BusinessServiceException("This product does not exist", ErrorType.BUSINESS_ERROR);

		productRepository.delete(productEntityFound);
	}

	private StoreEntity findStoreByName(String storeName){
		return storeRepository.findStoreByName(storeName);
	}

	private ProductEntity findProductByName(String productName){
		return productRepository.findProductByName(productName);
	}
}
