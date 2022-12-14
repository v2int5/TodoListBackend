package com.todolist.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.ToDoListBackendApplication;
import com.todolist.constant.SecurityConstant;
import com.todolist.controller.AuthController;
import com.todolist.model.AuthenticationRequest;
import com.todolist.model.UserModel;
import com.todolist.service.UserDetailsServiceImpl;
import com.todolist.service.UserService;
import com.todolist.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ToDoListBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserAuthenticationTests {

    @LocalServerPort
    private int port;

    @Mock
    private AuthenticationManager  authenticationManager;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    JwtUtil jwtUtil = new JwtUtil();
    @InjectMocks
    private AuthController authController = new AuthController(authenticationManager, userDetailsService, userService, jwtUtil);

    @BeforeEach
    void setUp() {

    }


    @Test
    public void shouldRegisterNewUserAndLogIn(){
        HashMap<String, String> user = new HashMap<>();
        user.put("username", "username");
        user.put("email", "email");
        user.put("password", "password");
        user.put("enabled", "true");
        user.put("roles", "USER");


        ResponseEntity<UserModel> registerResponseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/auth/register", user, UserModel.class);
        Assertions.assertEquals(HttpStatus.CREATED, registerResponseEntity.getStatusCode());

        AuthenticationRequest credentials = new AuthenticationRequest("username", "password");
        ResponseEntity<UserModel> responseEntity2 = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/auth/login", credentials, UserModel.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());

    }







}
