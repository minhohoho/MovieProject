package com.example.movieproject.service;

import com.example.movieproject.dto.kakaoApi.KakakoApiResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final KakaoUriBuilderService kakaoUriBuilderService;

    private final RestTemplate restTemplate;


    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;



    public KakakoApiResponseDTO getAddress(String addressName){

        if(ObjectUtils.isEmpty(addressName)) return null;

        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(addressName);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,"KakaoAK "+kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET,httpEntity,KakakoApiResponseDTO.class).getBody();
    }

}
