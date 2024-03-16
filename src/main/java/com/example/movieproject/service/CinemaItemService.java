package com.example.movieproject.service;

import com.example.movieproject.domain.CinemaItem;
import com.example.movieproject.dto.request.CinemaItemUpdateRequestDTO;
import com.example.movieproject.repository.CinemaItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CinemaItemService {

    private final CinemaItemRepository cinemaItemRepository;
    private final ImageService imageService;
    @Transactional
    public Boolean updateItemInfo(Long cinemaItemId, CinemaItemUpdateRequestDTO updateDTO){

        CinemaItem cinemaItem = cinemaItemRepository.findById(cinemaItemId)
                .orElseThrow();

        cinemaItem.updateCinemaItemInfo(updateDTO);

        return true;
    }
    @Transactional
    public Boolean deleteItemInfo(Long cinemaItemId){

        CinemaItem cinemaItem = cinemaItemRepository.findById(cinemaItemId)
                .orElseThrow();

        cinemaItemRepository.delete(cinemaItem);

        return true;
    }

    @Transactional
    public String  uploadCinemaItemImage(Long cinemaItemId, MultipartFile multipartFile){

        CinemaItem cinemaItem = cinemaItemRepository.findById(cinemaItemId)
                .orElseThrow();

        String imageUrl = imageService.uploadImage(multipartFile);

        cinemaItem.setImage(imageUrl);

        return imageUrl;
    }


}
