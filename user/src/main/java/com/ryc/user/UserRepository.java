package com.ryc.user;

import com.ryc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	@Query(value = "select * from users where email = :email", nativeQuery = true)
	UserEntity findUserByEmail(@Param(value = "email") String email);
}
