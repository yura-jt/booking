package com.railway.booking.entity;

import com.railway.booking.model.RoleType;

import java.util.Objects;

public class UserEntity {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final RoleType roleType;

    public UserEntity(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.password = builder.password;
        this.roleType = builder.roleType;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public RoleType getRoleType() {
        return roleType;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(UserEntity userEntity) {
        return new Builder().withId(userEntity.getId())
                .withFirstName(userEntity.getFirstName())
                .withLastName(userEntity.getLastName())
                .withEmail(userEntity.getEmail())
                .withPhoneNumber(userEntity.getPhoneNumber())
                .withRoleType(userEntity.getRoleType());
    }

    public static class Builder {
        private Integer id;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String password;
        private RoleType roleType;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withRoleType(RoleType roleType) {
            this.roleType = roleType;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }
}
