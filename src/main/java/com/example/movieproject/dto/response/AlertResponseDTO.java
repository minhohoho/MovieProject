package com.example.movieproject.dto.response;

import com.example.movieproject.common.type.AlertType;
import com.example.movieproject.domain.Alert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertResponseDTO {

    private Long alertId;
    private String sender;
    private boolean isRead;
    private AlertType alertType;


    public static AlertResponseDTO entityToDTO(Alert savedAlert){
        return AlertResponseDTO.builder()
                .alertId(savedAlert.getAlertId())
                .sender(savedAlert.getSender())
                .isRead(savedAlert.isRead())
                .alertType(savedAlert.getAlertType())
                .build();
    }


}
