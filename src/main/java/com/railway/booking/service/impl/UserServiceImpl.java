package com.railway.booking.service.impl;

import com.railway.booking.entity.UserDto;
import com.railway.booking.mapper.UserMapper;
import com.railway.booking.model.User;
import com.railway.booking.repository.UserRepository;
import com.railway.booking.service.PageUtil;
import com.railway.booking.service.UserService;
import com.railway.booking.service.validator.UserValidator;
import com.railway.booking.service.validator.ValidateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static final int USER_PER_PAGE = 5;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PageUtil pageUtil;
    private final UserMapper userMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserValidator userValidator,
                           PasswordEncoder passwordEncoder, PageUtil pageUtil, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
        this.pageUtil = pageUtil;
        this.userMapper = userMapper;
    }

    @Override
    public void register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            String message = String.format("User with such e-mail: %s is already exist", userDto.getEmail());
            LOGGER.warn(message);
        }
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        User user = userMapper.mapUserDtoToUser(userDto);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User login(String email, String password) {
        User user = null;
        if (!isValidCredentials(email, password)) {
            return user;
        }

        String encryptPassword = passwordEncoder.encode(password);
        user = userRepository.findByEmail(email).orElse(null);

        if (user == null || !user.getPassword().equals(encryptPassword)) {
            String message = String.format("User with email: %s is not registered or password is not correct", email);
            LOGGER.warn(message);
            user = null;
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll(int pageNumber) {
        int totalPage = (int) userRepository.count();
        int maxPage = pageUtil.getMaxPage(USER_PER_PAGE, totalPage);
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }

        return userRepository.findAll();
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
            LOGGER.warn(message);
        }
        return isValid;
    }
}