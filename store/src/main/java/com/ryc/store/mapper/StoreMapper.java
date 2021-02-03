package com.ryc.store.mapper;

import com.ryc.store.entity.StoreEntity;
import com.ryc.store.model.StoreModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {
	private final ModelMapper modelMapper;

	public StoreMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public StoreModel convertEntityToModel(StoreEntity storeEntity){
		StoreModel storeModel = modelMapper.map(storeEntity, StoreModel.class);
		return storeModel;
	}

	public StoreEntity convertModelToEntity(StoreModel storeModel){
		StoreEntity storeEntity = modelMapper.map(storeModel, StoreEntity.class);
		return storeEntity;
	}
}
