package com.example.movieproject.testUtils;

import com.example.movieproject.common.type.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithLoginMember> {
    @Override
    public SecurityContext createSecurityContext(WithLoginMember annotation) {

        String email = annotation.email();

        Authentication auth = new UsernamePasswordAuthenticationToken(email,"", List.of(new SimpleGrantedAuthority("USER")));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);

        return context;
    }
}
