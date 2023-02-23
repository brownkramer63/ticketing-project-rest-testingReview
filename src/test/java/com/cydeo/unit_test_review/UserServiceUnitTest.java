package com.cydeo.unit_test_review;

import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

//will let junit know we want to use thing within() in all of our tests
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

@Mock
    private UserRepository userRepository;
@Mock
    private ProjectService projectService;
@Mock
    private TaskService taskService;
@Mock
    private KeycloakService keycloakService;

@InjectMocks
private UserServiceImpl userService;

@Spy
    private UserMapper userMapper= new UserMapper(new ModelMapper());


}
