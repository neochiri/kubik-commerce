package com.ryc.store.service.impl;

import com.ryc.store.entity.StoreEntity;
import com.ryc.store.exception.BusinessServiceException;
import com.ryc.store.exception.ErrorType;
import com.ryc.store.mapper.StoreMapper;
import com.ryc.store.model.StoreModel;
import com.ryc.store.repository.StoreRepository;
import com.ryc.store.service.iface.StoreService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;
	private final StoreMapper storeMapper;

	public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper) {
		this.storeRepository = storeRepository;
		this.storeMapper = storeMapper;
	}

	@Override
	public List<StoreModel> findAllStores(String street, String city, double coordinateX, double coordinateY, String category, String owner) {
		List<StoreEntity> storeEntitiesFound = storeRepository.findAll(createQueryExample(street, city, coordinateX, coordinateY, category, owner));
		List<StoreModel> storeModelsFound = storeEntitiesFound.stream().map(store -> storeMapper.convertEntityToModel(store)).collect(Collectors.toList());
		return storeModelsFound;
	}

	@Override
	public StoreModel findStoreByName(String storeName) {
		StoreEntity storeEntityFound = storeRepository.findStoreByName(storeName);
		if(Objects.isNull(storeEntityFound)) throw new BusinessServiceException("This store does not exist", ErrorType.BUSINESS_ERROR);
		StoreModel storeModelFound = storeMapper.convertEntityToModel(storeEntityFound);
		return storeModelFound;
	}

	@Override
	public StoreModel createStore(StoreModel storeModelToCreate) {
		StoreEntity storeEntityFound = storeRepository.findStoreByName(storeModelToCreate.getName());
		if(Objects.nonNull(storeEntityFound)) throw new BusinessServiceException("This store already exist", ErrorType.BUSINESS_ERROR);

		StoreEntity storeEntityToCreate = storeMapper.convertModelToEntity(storeModelToCreate);
		StoreEntity storeEntityCreated = storeRepository.save(storeEntityToCreate);
		StoreModel storeModelCreated = storeMapper.convertEntityToModel(storeEntityCreated);
		return storeModelCreated;
	}

	@Override
	public StoreModel updateStore(String storeName, StoreModel storeModelToUpdate) {
		StoreEntity storeEntityFound = storeRepository.findStoreByName(storeName);
		if(Objects.isNull(storeEntityFound)) throw new BusinessServiceException("This store does not exist", ErrorType.BUSINESS_ERROR);

		StoreEntity storeEntityToUpdate = storeMapper.convertModelToEntity(storeModelToUpdate);
		storeEntityToUpdate.setId(storeEntityFound.getId());

		StoreEntity storeEntityUpdated = storeRepository.save(storeEntityToUpdate);
		StoreModel storeModelUpdated = storeMapper.convertEntityToModel(storeEntityUpdated);
		return storeModelUpdated;
	}

	@Override
	public void deleteStore(String storeName) {
		StoreEntity storeEntityFound = storeRepository.findStoreByName(storeName);
		if(Objects.isNull(storeEntityFound)) throw new BusinessServiceException("This store does not exist", ErrorType.BUSINESS_ERROR);

		storeRepository.delete(storeEntityFound);
	}

	private Example<StoreEntity> createQueryExample(String street, String city, double coordinateX, double coordinateY, String category, String owner){
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
		Example<StoreEntity> example = Example.of(createStoreForExample(street, city, coordinateX, coordinateY, category, owner), caseInsensitiveExampleMatcher);
		return example;
	}

	private StoreEntity createStoreForExample(String street, String city, double coordinateX, double coordinateY, String category, String owner){
		StoreEntity store = new StoreEntity();
		if(StringUtils.hasLength(category))store.setCategory(category);
		if(StringUtils.hasLength(street))store.setStreet(street);
		if(StringUtils.hasLength(city))store.setCity(city);
		if(coordinateX != 0.0) store.setCoordinateX(coordinateX);
		if(coordinateY != 0.0) store.setCoordinateY(coordinateY);
		if(StringUtils.hasLength(owner))store.setOwner(owner);
		return store;
	}
}
