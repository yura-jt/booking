package com.railway.booking.service.impl;

import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import com.railway.booking.mapper.UserMapper;
import com.railway.booking.model.UserDto;
import com.railway.booking.repository.UserRepository;
import com.railway.booking.service.validator.UserValidator;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String ENCODED_PASSWORD = "encoded_password";
    private static final Integer USER_ID = 1;
    private static final String FIRST_NAME = "Isaac";
    private static final String LAST_NAME = "Asimov";
    private static final String PASSWORD = "encoded_password";
    private static final String PHONE_NUMBER = "+47213245654";
    private static final String USER_EMAIL = "user@gmail.com";
    private static final RoleType ROLE_TYPE = RoleType.PASSENGER;
    private static final String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
    private static final String ENCODE_INCORRECT_PASSWORD = "encode_incorrect_password";

    private UserDto userDto = getUserDto();
    private User user = getUser();

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserValidator userValidator;

    @Mock
    private UserMapper userMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private UserServiceImpl userService;

    @After
    public void resetMocks() {
        reset(userRepository, passwordEncoder, userValidator);
    }

    @Test
    public void userShouldNotLoginAsThereIsNotUserWithSuchEmail() {
        when(passwordEncoder.encode(eq(PASSWORD))).thenReturn(ENCODED_PASSWORD);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        final User user = userService.login(USER_EMAIL, PASSWORD);

        assertNull(user);
        verify(passwordEncoder).encode(eq(PASSWORD));
        verify(userRepository).findByEmail(eq(USER_EMAIL));
    }

    @Test
    public void userShouldRegisterSuccessfully() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn(ENCODED_PASSWORD);
        when(userMapper.mapUserDtoToUser(any())).thenReturn(user);

        userService.register(userDto);

        verify(userRepository).findByEmail(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void userShouldNotRegisterAsEmailIsAlreadyUsed() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        userService.register(userDto);
        verifyZeroInteractions(passwordEncoder);
    }

    @Test
    public void findByIdShouldReturnSavedUser() {
        userService.register(userDto);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        final User actual = userService.findById(USER_ID);
        assertEquals(user, actual);
        verify(userRepository).findById(USER_ID);
    }

    @Test
    public void findByIdShouldReturnNull() {
        userService.register(userDto);
        when(userRepository.findById(USER_ID + 1)).thenReturn(Optional.empty());

        final User actual = userService.findById(USER_ID + 1);
        assertNull(actual);
        verify(userRepository).findById(USER_ID + 1);
    }

    @Test
    public void findByEmailShouldReturnNull() {
        userService.register(userDto);
        when(userRepository.findByEmail("1@mail")).thenReturn(Optional.empty());

        final User actual = userService.findByEmail("1@mail");
        assertNull(actual);
        verify(userRepository).findByEmail("1@mail");
    }

    private static UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword(PASSWORD);
        userDto.setPhoneNumber(PHONE_NUMBER);

        return userDto;
    }

    private User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setEmail(USER_EMAIL);
        user.setPassword(PASSWORD);
        user.setPhoneNumber(PHONE_NUMBER);
        user.setRoleType(ROLE_TYPE);
        return user;
    }
}