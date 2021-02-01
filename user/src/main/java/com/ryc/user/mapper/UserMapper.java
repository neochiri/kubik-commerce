package com.ryc.user.mapper;

import com.ryc.user.entity.UserEntity;
import com.ryc.user.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	private final ModelMapper modelMapper;

	public UserMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public UserModel entityToModel(UserEntity userEntity){
		UserModel user = modelMapper.map(userEntity, UserModel.class);
		return user;
	}

	public UserEntity modelToEntity(UserModel userModel){
		UserEntity user = modelMapper.map(userModel, UserEntity.class);
		return user;
	}
}
