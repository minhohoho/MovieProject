package com.example.movieproject.controller;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.dto.response.AlertResponseDTO;
import com.example.movieproject.service.AlertService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alert")
public class AlertController {

    private final AlertService alertService;

    @ApiOperation(value = "회원의 sse 구독 api")
    @GetMapping(value = "/subscribe",produces = "text/event-stream")
    public ResponseEntity<SseEmitter> subscribe(
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ){

        Long memberId = userPrincipal.getId();
        return ResponseEntity.ok().body(alertService.subscribe(memberId,lastEventId));
    }

    @ApiOperation(value = "회원의 모든 알람 조회 api")
    @GetMapping("/getAlertList")
    public ResponseEntity<List<AlertResponseDTO>> getAlertList(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){

        Long memberId = userPrincipal.getId();

        return ResponseEntity.ok().body(alertService.getAlertList(memberId));
    }

    @ApiOperation(value = "회원의 알림 읽음 처리 api")
    @PutMapping("/alertRead/{alertId}")
    public ResponseEntity<Void> alertRead(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long alertId
    ){

        Long memberId = userPrincipal.getId();

        alertService.alertRead(alertId,memberId);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "회원의 모든 알림 읽는 api")
    @PutMapping("/alertRead")
    public ResponseEntity<Void> AllRead(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){

        Long memberId = userPrincipal.getId();

        alertService.allRead(memberId);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "회원의 알림 삭제 api")
    @DeleteMapping("/delete/{alertId}")
    public ResponseEntity<Void> deleteAlert(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long alertId
    ){

        Long memberId = userPrincipal.getId();

        alertService.alertDelete(alertId,memberId);

        return ResponseEntity.ok().build();
    }

}
