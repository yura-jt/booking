package com.railway.booking.mapper;

import com.railway.booking.entity.UserEntity;
import com.railway.booking.model.User;

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
}