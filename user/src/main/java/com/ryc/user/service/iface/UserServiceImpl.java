package com.ryc.user.service.iface;

import com.ryc.user.UserRepository;
import com.ryc.user.entity.UserEntity;
import com.ryc.user.exception.BusinessServiceException;
import com.ryc.user.exception.ErrorType;
import com.ryc.user.mapper.UserMapper;
import com.ryc.user.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public UserModel createUser(UserModel userToCreate) {
		if(Objects.nonNull(findUserEntityByEmail(userToCreate.getEmail()))){
			throw new BusinessServiceException("The user already exists", ErrorType.BUSINESS_ERROR);
		}
		UserEntity userEntityToSave = userMapper.modelToEntity(userToCreate);
		UserEntity userEntitySaved = userRepository.save(userEntityToSave);
		UserModel userModelSaved = userMapper.entityToModel(userEntitySaved);
		return userModelSaved;
	}

	private UserEntity findUserEntityByEmail(String email){
		return userRepository.findUserByEmail(email);
	}
}
