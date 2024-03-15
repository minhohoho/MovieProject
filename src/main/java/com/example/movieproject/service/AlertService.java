package com.example.movieproject.service;

import com.example.movieproject.domain.Alert;
import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.Review;
import com.example.movieproject.dto.response.AlertResponseDTO;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.ReviewException;
import com.example.movieproject.repository.MemberRepository;
import com.example.movieproject.repository.alert.AlertRepository;
import com.example.movieproject.repository.alert.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertService {

    private final SseEmitterRepository sseEmitterRepository;
    private final AlertRepository alertRepository;
    private final MemberRepository memberRepository;

    private final Long DEFAULT_TIMEOUT= 120L * 1000 * 60;

    public SseEmitter subscribe(Long memberId,String lastEventId){

        String emitterId = memberId + "_" + System.currentTimeMillis();

        SseEmitter emitter = sseEmitterRepository.save(emitterId,new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(()->sseEmitterRepository.deleteById(emitterId));
        emitter.onTimeout(()->sseEmitterRepository.deleteById(emitterId));
        emitter.onError((e)->sseEmitterRepository.deleteById(emitterId));

        sendAlert(emitter,emitterId,emitterId,"EventStream Created. [memberId=" + memberId + "]");

        if(!lastEventId.isEmpty()){
            Map<String,Object> eventCaches = sseEmitterRepository.findAllEventCacheStartById(String.valueOf(memberId));
            eventCaches.entrySet().stream()
                    .filter(entry-> lastEventId.compareTo(entry.getKey())<0)
                    .forEach(entry->sendAlert(emitter,emitterId,entry.getKey(),entry.getValue()));
        }
        return emitter;
    }

    public void send(Alert saveAlert) {

        // 받을 사람 id
        Long memberId = saveAlert.getMember().getMemberId();
        String eventId = memberId + "_" + System.currentTimeMillis(); // 데이터 유실 시점 파악 위함 g2
        log.info(eventId);

        // 유저의 모든 SseEmitter 가져옴
        Map<String, SseEmitter> emitters = sseEmitterRepository.findAllStartById(memberId + "_");
        emitters.forEach(
                (key, emitter) -> {
                    sseEmitterRepository.saveEventCache(key, saveAlert.getAlertId());
                    sendAlert(emitter,
                            eventId,
                            key,
                            AlertResponseDTO.entityToDTO(saveAlert));
                }
        );
    }

    // 클라이언트에게 알림 전달하는 부분
    private void sendAlert(SseEmitter emitter, String eventId, String emitterId,
                           Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("message")
                    .data(data));
            log.info("알림 전송 완료");
        } catch (IOException exception) {
            sseEmitterRepository.deleteById(emitterId);
            log.error("SSE 연결이 올바르지 않습니다. 해당 memberId={}", eventId);
        }
    }

    @Transactional(readOnly = true)
    public List<AlertResponseDTO> getAlertList(Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow();

        return alertRepository.findByMemberInfo(member).stream().map(AlertResponseDTO::entityToDTO).toList();
    }
    @Transactional(readOnly = true)
    public void alertRead(Long alertId,Long memberId){

      Alert alert = alertRepository.findById(alertId)
              .orElseThrow();

      validate(alert,memberId);

      alert.changeIsRead(true);
    }

    @Transactional
    public void alertDelete(Long alertId,Long memberId){
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow();

        validate(alert,memberId);

        alertRepository.deleteById(alertId);
    }
    @Transactional
    public void allRead(Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow();

        List<Alert> alerts = alertRepository.findByMemberInfo(member);

        for(Alert alert:alerts){
            alert.changeIsRead(true);
        }

    }
    private void validate(Alert alert, Long memberId){
        if(Objects.equals(alert.getMember().getMemberId(),memberId)){
            throw new RuntimeException();
        }
    }



}
