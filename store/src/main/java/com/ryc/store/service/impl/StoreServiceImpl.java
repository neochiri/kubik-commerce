package com.ryc.store.service.impl;

import com.ryc.store.model.StoreModel;
import com.ryc.store.service.iface.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
	@Override
	public List<StoreModel> findAllStores() {
		return null;
	}

	@Override
	public StoreModel findStoreByName(String storeName) {
		return null;
	}

	@Override
	public StoreModel createStore(StoreModel storeModelToCreate) {
		return null;
	}

	@Override
	public StoreModel updateStore(StoreModel storeModelToUpdate) {
		return null;
	}

	@Override
	public void deleteStore(String storeName) {

	}
}
