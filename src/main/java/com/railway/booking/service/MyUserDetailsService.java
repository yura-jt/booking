package com.railway.booking.service;

import com.railway.booking.entity.RoleType;
import com.railway.booking.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("loadByUserName = " + email);
        User user = userService.findByEmail(email);
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoleType());
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(RoleType roleType) {
        System.out.println("GRANTING" + roleType.name());
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(roleType.name()));
        return roles;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        System.out.println("BUILD USER" + user);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                true, true, true, true, authorities);
    }
}