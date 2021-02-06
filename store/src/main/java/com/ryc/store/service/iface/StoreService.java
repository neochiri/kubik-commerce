package com.ryc.store.service.iface;

import com.ryc.store.model.StoreModel;

import java.util.List;

public interface StoreService {

	List<StoreModel> findAllStores();
	StoreModel findStoreByName(String storeName);
	StoreModel createStore(StoreModel storeModelToCreate);
	StoreModel updateStore(String storeName, StoreModel storeModelToUpdate);
	void deleteStore(String storeName);
}
