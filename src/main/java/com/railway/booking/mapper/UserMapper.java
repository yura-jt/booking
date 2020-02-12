package com.railway.booking.mapper;

import com.railway.booking.entity.UserDto;
import com.railway.booking.entity.UserEntity;
import com.railway.booking.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserEntity, User> {
    @Override
    public UserEntity mapDomainToEntity(User item) {
        return item == null ? null :
                UserEntity.builder()
                        .withId(item.getId())
                        .withFirstName(item.getFirstName())
                        .withLastName(item.getLastName())
                        .withEmail(item.getEmail())
                        .withPassword(item.getPassword())
                        .withPhoneNumber(item.getPhoneNumber())
                        .withRoleType(item.getRoleType())
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
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setRoleType(user.getRoleType());

        return userDto;
    }
}