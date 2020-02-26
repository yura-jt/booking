package com.railway.booking.service.impl;

import com.railway.booking.domain.ModelUser;
import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import com.railway.booking.mapper.UserMapper;
import com.railway.booking.repository.UserRepository;
import com.railway.booking.service.PageProvider;
import com.railway.booking.service.UserService;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.validator.UserValidator;
import com.railway.booking.service.validator.ValidateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PageProvider pageProvider;
    private final UserMapper userMapper;


    @Override
    public boolean register(ModelUser modelUser) {
        if (userRepository.findByEmail(modelUser.getEmail()).isPresent()) {
            String message = String.format("User with such e-mail: %s is already exist", modelUser.getEmail());
            log.warn(message);
            return false;
        }
        String encodedPassword = passwordEncoder.encode(modelUser.getPassword());
        modelUser.setPassword(encodedPassword);
        User user = userMapper.mapModelToEntity(modelUser);

        user.setRoleType(RoleType.PASSENGER);
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(String page) {
        int currentPage = pageProvider.getPageNumberFromString(page);

        int evalPage = (currentPage < 1) ? 1 : (currentPage - 1);
        evalPage = evalPage > pageProvider.getMaxPage(Constants.ITEM_PER_PAGE,
                (int) userRepository.count()) ? 0 : evalPage;

        return userRepository.findAll(PageRequest.of(evalPage, Constants.ITEM_PER_PAGE));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) {
        userValidator.validateId(id);
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new SecurityException("User with such email is not exist"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), getAuthorities(user));
        } else {
            String message = String.format("User with email: %s performed unsuccessful login attempt", email);
            log.warn(message);
            throw new SecurityException(message);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
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

    private List<SimpleGrantedAuthority> getAuthorities(User user) {
        return singletonList(new SimpleGrantedAuthority(user.getRoleType().name()));
    }
}