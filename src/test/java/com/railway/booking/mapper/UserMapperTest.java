package com.railway.booking.mapper;

import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import com.railway.booking.domain.UserDto;
import com.railway.booking.domain.UserEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class UserMapperTest {
    private final static UserMapper USER_MAPPER = new UserMapper();
    private static final Integer USER_ID = 1;
    private static final String FIRST_NAME = "Isaac";
    private static final String LAST_NAME = "Asimov";
    private static final String PASSWORD = "encoded_password";
    private static final String PHONE_NUMBER = "+47213245654";
    private static final String USER_EMAIL = "user@gmail.com";
    private static final RoleType ROLE_TYPE = RoleType.PASSENGER;

    private final User user = getUser();
    private final UserDto userDto = getUserDto();
    private final UserEntity userEntity = getUserEntity();


    @Test
    public void mapDomainToEntityShouldReturnCorrectEntity() {
        assertEquals(userEntity, USER_MAPPER.mapDomainToEntity(user));
    }

    @Test
    public void mapEntityToDomainShouldReturnCorrectDomainUser() {
        assertEquals(user, USER_MAPPER.mapEntityToDomain(userEntity));
    }

    @Test
    public void mapUserDtoToUserShouldReturnCorrectUser() {
        user.setId(null);
        assertEquals(user, USER_MAPPER.mapUserDtoToUser(userDto));
    }

    @Test
    public void mapUserToUserDtoShouldReturnCorrectDto() {
        assertEquals(userDto, USER_MAPPER.mapUserToUserDto(user));
    }

    @Test
    public void mapUserEntityToUserDto() {
        assertEquals(userDto, USER_MAPPER.mapUserEntityToUserDto(userEntity));
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

    private UserEntity getUserEntity() {
        return UserEntity.builder().id(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(USER_EMAIL)
                .password(PASSWORD)
                .phoneNumber(PHONE_NUMBER)
                .roleType(ROLE_TYPE)
                .build();
    }

    private UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword(PASSWORD);
        userDto.setPhoneNumber(PHONE_NUMBER);

        return userDto;
    }
}