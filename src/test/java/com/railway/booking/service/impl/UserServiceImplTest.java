package com.railway.booking.service.impl;

import com.railway.booking.model.RoleType;
import com.railway.booking.model.User;
import com.railway.booking.repository.UserRepository;
import com.railway.booking.service.PasswordEncryptor;
import com.railway.booking.service.validator.UserValidator;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
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

    private static final User USER = getUser();

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptor passwordEncryptor;
    @Mock
    private UserValidator userValidator;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private UserServiceImpl userService;

    @After
    public void resetMocks() {
        reset(userRepository, passwordEncryptor, userValidator);
    }

    @Test
    public void userShouldLoginSuccessfully() {
        when(passwordEncryptor.encrypt(eq(PASSWORD))).thenReturn(ENCODED_PASSWORD);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER));

        final User user = userService.login(USER_EMAIL, PASSWORD);

        assertNotNull(user);
        verify(passwordEncryptor).encrypt(eq(PASSWORD));
        verify(userRepository).findByEmail(eq(USER_EMAIL));
    }

    @Test
    public void userShouldNotLoginAsThereIsNotUserWithSuchEmail() {
//        expectedException.expect(EntityNotFoundException.class);
        when(passwordEncryptor.encrypt(eq(PASSWORD))).thenReturn(ENCODED_PASSWORD);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        final User user = userService.login(USER_EMAIL, PASSWORD);

        assertNull(user);
        verify(passwordEncryptor).encrypt(eq(PASSWORD));
        verify(userRepository).findByEmail(eq(USER_EMAIL));
    }

    @Test
    public void userShouldNotLoginAsPasswordIsIncorrect() {
//        expectedException.expect(EntityNotFoundException.class);
//        expectedException.expectMessage("User with email: " + USER_EMAIL +
//                " is not registered or password is not correct");
        when(passwordEncryptor.encrypt(eq(INCORRECT_PASSWORD))).thenReturn(ENCODE_INCORRECT_PASSWORD);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER));

        final User user = userService.login(USER_EMAIL, INCORRECT_PASSWORD);

        assertNull(user);
        verify(passwordEncryptor).encrypt(eq("INCORRECT_PASSWORD"));
        verify(userRepository).findByEmail(eq(USER_EMAIL));
    }

    @Test
    public void userShouldRegisterSuccessfully() {
        when(userValidator.isValid(any(User.class))).thenReturn(true);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.register(USER);

        verify(userValidator).isValid(any(User.class));
        verify(userRepository).findByEmail(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void userShouldNotRegisterWithInvalidPasswordOrEmail() {
//        expectedException.expect(ValidateException.class);
//        doThrow(ValidateException.class).when(userValidator).isValid(any(User.class));
        when(userValidator.isValid(any(User.class))).thenReturn(false);

        userService.register(USER);
        verify(userValidator).isValid(any(User.class));
    }

    @Test
    public void userShouldNotRegisterAsEmailIsAlreadyUsed() {
//        expectedException.expect(EntityAlreadyExistException.class);
        when(userValidator.isValid(any(User.class))).thenReturn(false);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER));
        when(userRepository.save(any(User.class)));

        userService.register(USER);
        verify(userValidator).isValid(any(User.class));
    }

    @Test
    public void findByIdShouldReturnSavedUser() {
        userService.register(USER);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));

        final User actual = userService.findById(USER_ID);
        assertEquals(USER, actual);
        verify(userRepository).findById(USER_ID);
    }

    @Test
    public void findByIdShouldReturnNull() {
        userService.register(USER);
        when(userRepository.findById(USER_ID + 1)).thenReturn(Optional.empty());

        final User actual = userService.findById(USER_ID + 1);
        assertNull(actual);
        verify(userRepository).findById(USER_ID + 1);
    }

    @Test
    public void findByEmailShouldReturnSavedUser() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(USER));

        final User actual = userService.findByEmail(USER_EMAIL);
        assertEquals(USER, actual);
        verify(userRepository).findByEmail(USER_EMAIL);
    }

    @Test
    public void findByEmailShouldReturnNull() {
        userService.register(USER);
        when(userRepository.findByEmail("1@mail")).thenReturn(Optional.empty());

        final User actual = userService.findByEmail("1@mail");
        assertNull(actual);
        verify(userRepository).findByEmail("1@mail");
    }

    private static User getUser() {
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