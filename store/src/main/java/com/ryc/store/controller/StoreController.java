package com.ryc.store.controller;

import com.ryc.store.model.ProductModel;
import com.ryc.store.model.StoreModel;
import com.ryc.store.service.iface.ProductService;
import com.ryc.store.service.iface.StoreService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/store")
public class StoreController {

	private final StoreService storeService;
	private final ProductService productService;

	public StoreController(StoreService storeService, ProductService productService) {
		this.storeService = storeService;
		this.productService = productService;
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StoreModel>> getAllStores(){
		List<StoreModel> storesFound = storeService.findAllStores();
		return new ResponseEntity<>(storesFound, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "/{storeName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StoreModel> findStoreByName(@PathVariable String storeName){
		StoreModel storeModelFound = storeService.findStoreByName(storeName);
		return new ResponseEntity<>(storeModelFound, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StoreModel> createStore(@RequestBody StoreModel storeToCreate){
		StoreModel store = storeService.createStore(storeToCreate);
		return new ResponseEntity<>(store, new HttpHeaders(), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{storeName}")
	public ResponseEntity<StoreModel> updateStore(@PathVariable String storeName, @RequestBody StoreModel storeToUpdate){
		StoreModel store = storeService.updateStore(storeName, storeToUpdate);
		return new ResponseEntity<>(store, new HttpHeaders(), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{storeName}")
	public ResponseEntity deleteStore(@PathVariable String storeName){
		storeService.deleteStore(storeName);
		return new ResponseEntity(new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/{storeName}/product/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductModel>> getAllProductsByStore(@PathVariable String storeName){
		List<ProductModel> productsFound = productService.findAllProductsByStore(storeName);
		return new ResponseEntity<>(productsFound, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "/{storeName}/product/{productName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductModel> findProductByStore(@PathVariable String storeName, @PathVariable String productName){
		ProductModel productFound = productService.findProductByStore(storeName, productName);
		return new ResponseEntity<>(productFound, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping(value = "/{storeName}/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductModel> createProduct(@RequestBody ProductModel productToCreate, @PathVariable String storeName){
		ProductModel productCreated = productService.createProduct(storeName, productToCreate);
		return new ResponseEntity<>(productCreated, new HttpHeaders(), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{storeName}/product/{productName}")
	public ResponseEntity<ProductModel> updateProduct(@PathVariable String storeName, @RequestBody ProductModel productToUpdate, @PathVariable String productName){
		ProductModel store = productService.updateProduct(storeName, productName, productToUpdate);
		return new ResponseEntity<>(store, new HttpHeaders(), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{storeName}/product/{productName}")
	public ResponseEntity deleteProduct(@PathVariable String storeName, @PathVariable String productName){
		productService.deleteProduct(storeName, productName);
		return new ResponseEntity(new HttpHeaders(), HttpStatus.NO_CONTENT);
	}
}
