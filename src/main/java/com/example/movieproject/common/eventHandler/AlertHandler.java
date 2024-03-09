package com.example.movieproject.common.eventHandler;


import com.example.movieproject.domain.Alert;
import com.example.movieproject.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlertHandler {

    private final AlertService alertService;

    @TransactionalEventListener
    @Async
    public void alertHandler(Alert saveAlert){
        alertService.send(saveAlert);
    }


}
