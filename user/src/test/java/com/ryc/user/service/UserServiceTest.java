package com.ryc.user.service;

import com.ryc.user.UserRepository;
import com.ryc.user.entity.UserEntity;
import com.ryc.user.exception.BusinessServiceException;
import com.ryc.user.mapper.UserMapper;
import com.ryc.user.model.UserModel;
import com.ryc.user.service.iface.UserServiceImpl;
import com.ryc.user.utils.FileJsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
public class UserServiceTest {

	private final String USER_MODEL_JSON = "user-model.json";
	private final String USER_ENTITY_JSON = "user-entity.json";

	private UserEntity userEntity;
	private UserModel userModel;

	@InjectMocks
	private UserServiceImpl userService;
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserServiceTest(@Mock UserRepository userRepository, @Mock UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@BeforeEach
	public void setUp(){
		userEntity = (UserEntity) FileJsonConverter.getObjectFromJsonFile(USER_ENTITY_JSON, UserEntity.class);
		userModel = (UserModel) FileJsonConverter.getObjectFromJsonFile(USER_MODEL_JSON, UserModel.class);
	}

	@Test
	public void testCreateUserOK(){
		when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(null);
		when(userMapper.modelToEntity(Mockito.any(UserModel.class))).thenReturn(userEntity);
		when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
		when(userMapper.entityToModel(Mockito.any(UserEntity.class))).thenReturn(userModel);

		UserModel userModelCreated = userService.createUser(userModel);

		assertEquals(userModel, userModelCreated);
	}

	@Test
	public void testCreateUserAlreadyExists(){
		when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(new UserEntity());

		BusinessServiceException exception = assertThrows(BusinessServiceException.class, () -> userService.createUser(userModel));

		assertEquals(exception.getMessage(), "The user already exists");
	}
}
