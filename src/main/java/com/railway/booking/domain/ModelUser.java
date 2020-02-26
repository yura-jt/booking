package com.railway.booking.domain;

import com.railway.booking.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class ModelUser {

    private Integer id;

    @NotEmpty(message = "{empty.first.name}")
    private String firstName;

    @NotEmpty(message = "{empty.last.name}")
    private String lastName;

    @Email(message = "{invalid.email}")
    @NotEmpty(message = "{empty.email}")
    private String email;

    @NotEmpty(message = "{empty.phone.number}")
    private String phoneNumber;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$", message = "{invalid.password}")
    private String password;

    private String repeatedPassword;

    private RoleType roleType;

}
