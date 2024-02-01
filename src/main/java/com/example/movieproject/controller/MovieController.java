package com.example.movieproject.controller;


import com.example.movieproject.dto.request.MovieCreateRequestDTO;
import com.example.movieproject.dto.response.MovieCreateResponseDTO;
import com.example.movieproject.service.MovieService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;
    @ApiOperation(value="영화 생성 api",notes = "영화 엔티티를 생성하고 영화에 소속된 스태프도 저장한다")
    @PostMapping("/create")
    public ResponseEntity<MovieCreateResponseDTO> createMovie(
            @RequestBody MovieCreateRequestDTO requestDTO){

        MovieCreateResponseDTO responseDTO = movieService.createMovie(requestDTO);

        return ResponseEntity.ok().body(responseDTO);
    }




}
