package com.ryc.store.repository;

import com.ryc.store.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

	@Query(value = "select * from product where name = :name", nativeQuery = true)
	ProductEntity findProductByName(@Param("name") String name);
}
