package com.example.movieproject.controller;


import com.example.movieproject.dto.request.MovieCreateRequestDTO;
import com.example.movieproject.dto.request.SearchRequestDTO;
import com.example.movieproject.dto.response.MovieCreateResponseDTO;
import com.example.movieproject.dto.response.MovieListResponseDTO;
import com.example.movieproject.dto.response.MovieStaffResponseDTO;
import com.example.movieproject.dto.response.MovieWithScoreResponseDTO;
import com.example.movieproject.service.MovieService;
import com.example.movieproject.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;

    @ApiOperation(value="영화 생성 api",notes = "영화 엔티티를 생성하고 영화에 소속된 스태프도 저장한다")
    @PostMapping("/create")
    //@Secured("{}")
    public ResponseEntity<MovieCreateResponseDTO> createMovie(
            @RequestBody MovieCreateRequestDTO requestDTO){

        MovieCreateResponseDTO responseDTO = movieService.createMovie(requestDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

    @ApiOperation(value="영화 전체 검색 api && 영화 검색", notes = "영화 엔티티를 전체를 가져오고 개봉일을 기준으로 정렬하고 페이징 처리했다 ")
    @GetMapping("/searchMovieList")
    public ResponseEntity<Page<MovieListResponseDTO>> searchMovieList(
            @RequestBody SearchRequestDTO searchRequestDTO,
            @PageableDefault(size=10,sort="openingDate",direction = Sort.Direction.DESC) Pageable pageable){


        Page<MovieListResponseDTO> responseDTO= movieService.searchMovieList(pageable,searchRequestDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

    @ApiOperation(value="영화 단건 조회",notes = "영화에 대한 정보를 가져오고, 평균 평점을 구해온다")
    @GetMapping("/getMovie/{movieId}")
    public ResponseEntity<MovieWithScoreResponseDTO> getMovie(
            @PathVariable Long movieId){
        double averageScore = reviewService.getMovieReviewScore(movieId);

         MovieWithScoreResponseDTO movieWithScoreResponseDTO=movieService.getMovie(movieId,averageScore);

        return ResponseEntity.ok().body(movieWithScoreResponseDTO);
    }







}
