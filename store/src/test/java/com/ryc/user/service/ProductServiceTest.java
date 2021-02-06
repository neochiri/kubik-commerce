package com.ryc.user.service;

import com.ryc.store.entity.ProductEntity;
import com.ryc.store.entity.StoreEntity;
import com.ryc.store.exception.BusinessServiceException;
import com.ryc.store.mapper.ProductMapper;
import com.ryc.store.model.ProductModel;
import com.ryc.store.repository.ProductRepository;
import com.ryc.store.repository.StoreRepository;
import com.ryc.store.service.impl.ProductServiceImpl;
import com.ryc.store.util.FileJsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
public class ProductServiceTest {

	private final String USER_MODEL_JSON = "user-model.json";
	private final String USER_ENTITY_JSON = "user-entity.json";

	private ProductEntity productEntity;
	private ProductModel productModel;

	@InjectMocks
	private ProductServiceImpl productService;
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final StoreRepository storeRepository;

	public ProductServiceTest(@Mock ProductRepository productRepository, @Mock ProductMapper productMapper, @Mock StoreRepository storeRepository) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.storeRepository = storeRepository;
	}

	@BeforeEach
	public void setUp(){
		productEntity = (ProductEntity) FileJsonConverter.getObjectFromJsonFile(USER_ENTITY_JSON, ProductEntity.class);
		productModel = (ProductModel) FileJsonConverter.getObjectFromJsonFile(USER_MODEL_JSON, ProductModel.class);
	}

	@Test
	public void testCreateProductOK(){
		when(storeRepository.findStoreByName(Mockito.anyString())).thenReturn(new StoreEntity());
		when(productRepository.findProductByName(Mockito.anyString())).thenReturn(null);
		when(productMapper.convertModelToEntity(Mockito.any(ProductModel.class))).thenReturn(productEntity);
		when(productRepository.save(Mockito.any(ProductEntity.class))).thenReturn(productEntity);
		when(productMapper.convertEntityToModel(Mockito.any(ProductEntity.class))).thenReturn(productModel);

		ProductModel productModelCreated = productService.createProduct("StoreTest", productModel);

		assertEquals(productModelCreated, productModel);
	}

	@Test
	public void testCreateProductStoreNotExist(){
		when(storeRepository.findStoreByName(Mockito.anyString())).thenReturn(null);

		BusinessServiceException exception = assertThrows(BusinessServiceException.class, () -> productService.createProduct("", productModel));

		assertEquals(exception.getMessage(), "This store does not exist");
	}

	@Test
	public void testCreateProductNotExist(){
		when(storeRepository.findStoreByName(Mockito.anyString())).thenReturn(new StoreEntity());
		when(productRepository.findProductByName(Mockito.anyString())).thenReturn(new ProductEntity());

		BusinessServiceException exception = assertThrows(BusinessServiceException.class, () -> productService.createProduct("StoreTest", productModel));

		assertEquals(exception.getMessage(), "This product already exist");
	}
}
