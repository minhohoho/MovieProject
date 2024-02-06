package com.example.movieproject.repository.impl;

import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.dto.request.SearchRequestDTO;
import com.example.movieproject.dto.response.MovieListResponseDTO;
import com.example.movieproject.repository.MovieSearchCustomRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import com.querydsl.core.types.Projections;

import java.util.Date;
import java.util.List;

import static com.example.movieproject.domain.QMovie.movie;

@Repository
@RequiredArgsConstructor
public class MovieSearchCustomRepositoryImpl  implements MovieSearchCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MovieListResponseDTO> searchMovieList(Pageable pageable,SearchRequestDTO searchRequestDTO){

        QueryResults<MovieListResponseDTO> results = queryFactory
                .select(Projections.constructor(MovieListResponseDTO.class,
                        movie.MovieId,
                        movie.title,
                        movie.age,
                        movie.openingDate,
                        movie.duringTime,
                        movie.movieTheme))
                .from(movie)
                .where( searchTitle(searchRequestDTO.getTitle()),
                        searchOpeningDate(searchRequestDTO.getOpeningDate()),
                        searchMovieTheme(searchRequestDTO.getMovieTheme()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MovieListResponseDTO> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);
    }

    private BooleanExpression searchTitle(String title){
        return title==null ? null : movie.title.eq(title);
    }

    private BooleanExpression searchOpeningDate(Date openingDate){
        return openingDate==null ? null : movie.openingDate.goe(openingDate);
    }

    private BooleanExpression searchMovieTheme(MovieTheme movieTheme){
        return movieTheme==null ? null : movie.movieTheme.eq(movieTheme);
    }


}
