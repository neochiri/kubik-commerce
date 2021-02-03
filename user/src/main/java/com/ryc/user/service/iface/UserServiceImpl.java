package com.ryc.user.service.iface;

import com.ryc.user.UserRepository;
import com.ryc.user.entity.UserEntity;
import com.ryc.user.exception.BusinessServiceException;
import com.ryc.user.exception.ErrorType;
import com.ryc.user.mapper.UserMapper;
import com.ryc.user.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public List<UserModel> findAllUsers() {
		List<UserEntity> userEntitiesFound = userRepository.findAll();
		List<UserModel> userModelsFound = userEntitiesFound.stream().map(userEntity -> userMapper.entityToModel(userEntity)).collect(Collectors.toList());
		return userModelsFound;
	}

	@Override
	public UserModel findUser(String email) {
		UserEntity userEntityFound = findUserEntityByEmail(email);
		if(Objects.isNull(userEntityFound)) throw new BusinessServiceException("This user does not exist", ErrorType.BUSINESS_ERROR);
		UserModel userModel = userMapper.entityToModel(userEntityFound);
		return userModel;
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

	@Override
	public UserModel updateUser(String email, UserModel userToUpdate) {
		UserEntity userEntityFound = findUserEntityByEmail(email);
		if(Objects.isNull(userEntityFound)) throw new BusinessServiceException("This user does not exist", ErrorType.BUSINESS_ERROR);
		UserEntity userEntityToUpdate = userMapper.modelToEntity(userToUpdate);
		userEntityToUpdate.setId(userEntityFound.getId());
		UserEntity userEntityUpdated = userRepository.save(userEntityToUpdate);
		UserModel userModelUpdated = userMapper.entityToModel(userEntityUpdated);
		return userModelUpdated;
	}

	@Override
	public void deleteUser(String email) {
		UserEntity userEntityFound = findUserEntityByEmail(email);
		if(Objects.isNull(userEntityFound)) throw new BusinessServiceException("This user does not exist", ErrorType.BUSINESS_ERROR);
		userRepository.delete(userEntityFound);
	}

	private UserEntity findUserEntityByEmail(String email){
		return userRepository.findUserByEmail(email);
	}
}
