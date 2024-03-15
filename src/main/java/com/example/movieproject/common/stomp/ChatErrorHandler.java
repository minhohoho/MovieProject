package com.example.movieproject.common.stomp;

import com.example.movieproject.exceptionHandle.ErrorList;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ChatErrorHandler extends StompSubProtocolErrorHandler {

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> message,Throwable ex) {

        if("UN".equals(ex.getMessage())){
            return errorMessage("유효하지 않은 권한입니다");
        }

         return super.handleClientMessageProcessingError(message,ex);
    }

    private Message<byte[]> errorMessage(String errorMessage){

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(errorMessage.getBytes(StandardCharsets.UTF_8),accessor.getMessageHeaders());
    }





}
