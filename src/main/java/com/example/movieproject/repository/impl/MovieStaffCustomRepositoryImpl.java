package com.example.movieproject.repository.impl;

import com.example.movieproject.dto.response.MovieStaffResponseDTO;
import com.example.movieproject.dto.response.StaffResponseDTO;
import com.example.movieproject.repository.MovieStaffCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import com.querydsl.core.types.Projections;

import static com.example.movieproject.domain.QMovieStaff.movieStaff;
import static com.example.movieproject.domain.QMovie.movie;
import static com.example.movieproject.domain.QStaff.staff;

@Repository
@RequiredArgsConstructor
public class MovieStaffCustomRepositoryImpl implements MovieStaffCustomRepository {

    private final JPAQueryFactory queryFactory;
    @Override
    public MovieStaffResponseDTO findMovieAndStaffInfo(Long movieId) {
        return queryFactory
                .from(movieStaff)
                .where(movieStaff.movie.MovieId.in(movieId))
                .join(movieStaff.movie,movie)
                .join(movieStaff.staff,staff)
                .transform(groupBy(movieStaff.movie.MovieId)
                        .as(Projections.constructor(
                          MovieStaffResponseDTO.class,
                                movieStaff.movie.MovieId,
                                movieStaff.movie.title,
                                movieStaff.movie.age,
                                movieStaff.movie.duringTime,
                                movieStaff.movie.movieTheme,
                          list(Projections.constructor(
                                  StaffResponseDTO.class,
                                  movieStaff.staff.name,
                                  movieStaff.role
                          ))
                        ))).get(movieId);
    }
}
