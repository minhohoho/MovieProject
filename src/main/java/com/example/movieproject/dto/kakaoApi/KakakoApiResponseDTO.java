package com.example.movieproject.dto.kakaoApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakakoApiResponseDTO {

    @JsonProperty("meta")
    private MetaDTO metaDto;

    @JsonProperty("documents")
    private List<DocumentDTO> documentList;

}
