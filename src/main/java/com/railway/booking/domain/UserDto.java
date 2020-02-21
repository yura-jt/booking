package com.railway.booking.domain;

import com.railway.booking.entity.RoleType;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private final RoleType roleType = RoleType.PASSENGER;

}