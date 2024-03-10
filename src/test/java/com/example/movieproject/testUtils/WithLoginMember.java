package com.example.movieproject.testUtils;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithLoginMember {

    long memberId() default 1L;
    String email() default "sam6754@naver.com";
    String name() default  "민호";


}
