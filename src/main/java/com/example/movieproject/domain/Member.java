package com.example.movieproject.domain;

import com.example.movieproject.common.oauth2.info.OAuth2UserInfo;
import com.example.movieproject.common.type.AuthProvider;
import com.example.movieproject.common.type.UserRole;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memberId")
    private Long memberId;

    @Column(nullable = false,unique = true)
    private String email;

    private String name;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    public Member update(OAuth2UserInfo oAuth2UserInfo){
        this.name = oAuth2UserInfo.getName();
        this.oauth2Id = oAuth2UserInfo.getOAuth2Id();

        return this;
    }


}
