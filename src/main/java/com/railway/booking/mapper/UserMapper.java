package com.railway.booking.mapper;

import com.railway.booking.entity.User;
import com.railway.booking.model.UserDto;
import com.railway.booking.model.UserEntity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserEntity, User> {
    @Override
    public UserEntity mapDomainToEntity(User item) {
        return item == null ? null :
                UserEntity.builder().id(item.getId())
                        .firstName(item.getFirstName())
                        .lastName(item.getLastName())
                        .email(item.getEmail())
                        .password(item.getPassword())
                        .phoneNumber(item.getPhoneNumber())
                        .roleType(item.getRoleType())
                        .build();
    }

    @Override
    public User mapEntityToDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        User user = new User();
        user.setId(entity.getId());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setRoleType(entity.getRoleType());

        return user;
    }

    public User mapUserDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRoleType(userDto.getRoleType());
        return user;
    }

    public UserDto mapUserToUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());

        return userDto;
    }

    public UserDto mapUserEntityToUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setPhoneNumber(userEntity.getPhoneNumber());
        return userDto;
    }
}