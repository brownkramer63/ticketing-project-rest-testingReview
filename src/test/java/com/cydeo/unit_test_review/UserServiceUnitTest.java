package com.cydeo.unit_test_review;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
//now we are adding in our objects
    User user;
    UserDTO userDTO;

    //then we add in our before each to make our mock objects
    //this will run before each of our tests
    @BeforeEach
    void setUp(){
         user= new User();
         user.setId(1L);
         user.setFirstName("Kramer");
         user.setLastName("Brown");
         user.setUserName("user");
         user.setPassWord("Abc1");
         user.setEnabled(true);
         user.setRole(new Role("Manager"));
         //we wont need address information for the test we will do

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("Kramer");
        userDTO.setLastName("Brown");
        userDTO.setUserName("user");
        userDTO.setPassWord("Abc1");
        userDTO.setEnabled(true);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setDescription("Manager");

        userDTO.setRole(roleDTO);


    }



}
