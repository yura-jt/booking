package com.railway.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    private UserService userService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .csrf().disable()
//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
//                .passwordParameter("password");
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(userAuthenticationProvider());
//    }
//
//    @Bean
//    public AuthenticationProvider userAuthenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService());
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        System.out.println("\n....userDetailsService");
//        return email -> {
//            User user = userService.findByEmail(email);
//            return buildSpringSecurityUser(user);
//        };
//    }
//
//    private UserDetails buildSpringSecurityUser(User user) {
//        System.out.println("\n....buildSpringSecurityUser");
//        System.out.println(user);
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getEmail())
//                .password(user.getPassword())
//                .roles(fetchRoleNamesArray(user))
//                .build();
//    }
//
//    private String[] fetchRoleNamesArray(User user) {
//        return new String[]{user.getRoleType().name()};
//    }
}