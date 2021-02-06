package com.ryc.store.repository;

import com.ryc.store.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {

	@Query(value = "select * from store where name = :name", nativeQuery = true)
	StoreEntity findStoreByName(@Param("name") String name);
}
