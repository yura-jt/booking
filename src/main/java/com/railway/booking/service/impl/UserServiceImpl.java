package com.railway.booking.service.impl;

import com.railway.booking.entity.User;
import com.railway.booking.mapper.UserMapper;
import com.railway.booking.model.UserDto;
import com.railway.booking.repository.UserRepository;
import com.railway.booking.service.PageProvider;
import com.railway.booking.service.UserService;
import com.railway.booking.service.validator.UserValidator;
import com.railway.booking.service.validator.ValidateException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private static final int USER_PER_PAGE = 5;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PageProvider pageProvider;
    private final UserMapper userMapper;


    @Override
    public boolean register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            String message = String.format("User with such e-mail: %s is already exist", userDto.getEmail());
            log.warn(message);
            return false;
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        User user = userMapper.mapUserDtoToUser(userDto);
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public User login(String email, String password) {
        return validateUser(email, password);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(String page) {
        int currentPage = pageProvider.getPageNumberFromString(page);

        int evalPage = (currentPage < 1) ? 1 : (currentPage - 1);
        evalPage = evalPage > pageProvider.getMaxPage(USER_PER_PAGE, (int) userRepository.count()) ? 0 : evalPage;

        return userRepository.findAll(PageRequest.of(evalPage, USER_PER_PAGE));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Integer id) {
        userValidator.validateId(id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    private boolean isValidCredentials(String email, String password) {
        boolean isValid = false;
        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userValidator.isValid(user);
            isValid = true;
        } catch (ValidateException e) {
            String message = String.format("Credentials, provided for email: %s are not valid ", email);
            log.warn(message);
        }
        return isValid;
    }

    private User validateUser(String email, String password) {
        if (!isValidCredentials(email, password)) {
            String message = String.format("User with such e-mail: %s is already exist", email);
            log.warn(message);
            return null;
        }
        User user = userRepository.findByEmail(email).orElse(null);

        String encryptPassword = passwordEncoder.encode(password);
        return isPasswordValid(user, encryptPassword) ? user : null;
    }

    private boolean isPasswordValid(User user, String encryptPassword) {
        if (user == null || !user.getPassword().equals(encryptPassword)) {
            log.warn("Provided password is not matched");
            return false;
        }
        return true;
    }
}