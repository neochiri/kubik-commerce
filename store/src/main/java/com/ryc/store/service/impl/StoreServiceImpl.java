package com.ryc.store.service.impl;

import com.ryc.store.entity.StoreEntity;
import com.ryc.store.exception.BusinessServiceException;
import com.ryc.store.exception.ErrorType;
import com.ryc.store.mapper.StoreMapper;
import com.ryc.store.model.StoreModel;
import com.ryc.store.repository.StoreRepository;
import com.ryc.store.service.iface.StoreService;
import org.springframework.stereotype.Service;

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
	public List<StoreModel> findAllStores() {
		List<StoreEntity> storeEntitiesFound = storeRepository.findAll();
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

}
