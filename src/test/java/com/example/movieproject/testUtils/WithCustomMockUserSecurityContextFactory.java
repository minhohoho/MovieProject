package com.example.movieproject.testUtils;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.common.type.UserRole;
import com.example.movieproject.domain.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;
import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithLoginMember> {
    @Override
    public SecurityContext createSecurityContext(WithLoginMember annotation) {

        UserPrincipal userPrincipal = new UserPrincipal(annotation.memberId(), annotation.email(), Collections.singleton(new SimpleGrantedAuthority("USER")));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal,"",userPrincipal.getAuthorities());

        SecurityContext context =SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        return context;
    }
}
