package com.railway.booking.config;

import com.railway.booking.entity.RoleType;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component
@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException, ServletException, RuntimeException {
        HttpSession session = httpServletRequest.getSession();
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("username", email);

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        authorities.forEach(authority ->
        {
            if (authority.getAuthority().equals("ADMIN")) {
                session.setAttribute("role", RoleType.ADMIN);
                try {
                    httpServletResponse.sendRedirect("/admin/panel");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (authority.getAuthority().equals("PASSENGER")) {
                session.setAttribute("role", RoleType.PASSENGER);
                try {
                    httpServletResponse.sendRedirect("/user/profile");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}