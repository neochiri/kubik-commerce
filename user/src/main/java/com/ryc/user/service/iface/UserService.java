package com.ryc.user.service.iface;

import com.ryc.user.model.UserModel;

import java.util.List;

public interface UserService {

	List<UserModel> findAllUsers();
	UserModel findUser(String email);
	UserModel createUser(UserModel userToCreate);
	UserModel updateUser(String email, UserModel userToUpdate);
	void deleteUser(String email);
}
