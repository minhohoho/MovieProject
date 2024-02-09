package com.example.movieproject.common.oauth2.handler;

import com.example.movieproject.common.jwt.JwtProvider;
import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.common.type.UserRole;
import com.example.movieproject.dto.response.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

      private JwtProvider jwtProvider;

      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

          UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

          String email = userPrincipal.getEmail();

          UserRole userRole = UserRole.valueOf(userPrincipal.getAuthorities().stream()
                  .findFirst()
                  .orElseThrow(IllegalAccessError::new)
                  .getAuthority());

          String targetUrl = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/loginSucess")
                  .queryParam("accessToken","asar3r4rwrsfd")
                  .build()
                  .encode(StandardCharsets.UTF_8)
                  .toUriString();


          getRedirectStrategy().sendRedirect(request,response,targetUrl);
      }



}
