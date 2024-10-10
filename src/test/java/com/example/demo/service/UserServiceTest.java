package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;



@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;
    private User user;

    @BeforeEach
    void initData(){
        dob = LocalDate.of(1990,1,1);
        request = UserCreationRequest.builder()
                .username("John")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .build();

        userResponse = UserResponse.builder()
                .id("cgs1234455633")
                .username("John")
                .firstName("John")
                .lastName("Doe")
                .build();
        user = User.builder()
                .id("cgs1234455633")
                .username("John")
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    @Test
    void createUser_validRequest_Success(){
        //when
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        //given
        var response = userService.createUser(request);
        //then
        Assertions.assertThat(response.getId()).isEqualTo("cgs1234455633");
        Assertions.assertThat(response.getUsername()).isEqualTo("John");

    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }
}
