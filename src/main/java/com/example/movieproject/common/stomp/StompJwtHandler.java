package com.example.movieproject.common.stomp;

import com.example.movieproject.common.jwt.JwtProvider;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE+99)
public class StompJwtHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);

        if(StompCommand.CONNECT.equals(accessor.getCommand())){

            String authToken = accessor.getFirstNativeHeader("Authorization");

            if( authToken != null){

                Authentication  authentication = jwtProvider.getAuthentication(authToken);

                SecurityContextHolder.getContext().setAuthentication(authentication);

//                new UserPrincipal()

                accessor.setUser(authentication);
            }else{
                throw new MessageDeliveryException("UNAUTHORIZED");
            }

        }



        return message;
    }
}
