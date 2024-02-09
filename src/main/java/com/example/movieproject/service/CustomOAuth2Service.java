package com.example.movieproject.service;

import com.example.movieproject.common.oauth2.info.OAuth2UserInfo;
import com.example.movieproject.common.oauth2.info.OAuth2UserInfoFactory;
import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.common.type.AuthProvider;
import com.example.movieproject.common.type.UserRole;
import com.example.movieproject.domain.Member;
import com.example.movieproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);


        return proccessOAuth2User(userRequest,oAuth2User);
    }

    protected OAuth2User proccessOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User){
        AuthProvider authProvider =AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());


        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());



        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }

        Member member = memberRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);

        if(member != null){
            if(!member.getAuthProvider().equals(authProvider)){
                throw new RuntimeException("Email already signed up.");
            }
            member = updateUser(member,oAuth2UserInfo);
        }else{
            member = registerMember(authProvider,oAuth2UserInfo);
        }


        return UserPrincipal.create(member,oAuth2UserInfo.getAttributes());
    }

    private Member registerMember(AuthProvider authProvider, OAuth2UserInfo oAuth2UserInfo){

        Member member = Member.builder()
                .email(oAuth2UserInfo.getEmail())
                .name(oAuth2UserInfo.getName())
                .oauth2Id(oAuth2UserInfo.getOAuth2Id())
                .email(oAuth2UserInfo.getEmail())
                .authProvider(authProvider)
                .userRole(UserRole.USER)
                .build();

        return memberRepository.save(member);
    }


    private Member updateUser(Member user, OAuth2UserInfo oAuth2UserInfo) {
        return null;
    }




}
