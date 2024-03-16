package com.example.movieproject.repository.impl;

import com.example.movieproject.dto.response.CinemaItemResponseDTO;
import com.example.movieproject.dto.response.MyCinemaResponseDTO;
import com.example.movieproject.repository.CinemaItemCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import com.querydsl.core.types.Projections;

import static com.example.movieproject.domain.QCinemaItem.cinemaItem;
import static com.example.movieproject.domain.QMyCinema.myCinema;

@Repository
@RequiredArgsConstructor
public class CinemaItemCustomRepositoryImpl implements CinemaItemCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public MyCinemaResponseDTO getMyCinemaWithItemList(Long myCinemaId) {
        return queryFactory
                .from(cinemaItem)
                .where(cinemaItem.myCinema.myCinemaId.in(myCinemaId))
                .join(cinemaItem.myCinema,myCinema)
                .transform(groupBy(cinemaItem.myCinema.myCinemaId)
                        .as(Projections.constructor(MyCinemaResponseDTO.class,
                                cinemaItem.myCinema.myCinemaId,
                                cinemaItem.myCinema.cinemaName,
                                cinemaItem.myCinema.cinemaDetail,
                                cinemaItem.myCinema.addressName,
                                cinemaItem.myCinema.latitude,
                                cinemaItem.myCinema.longitude,
                                cinemaItem.myCinema.myCinemaImageUrl,
                                list(Projections.constructor(
                                        CinemaItemResponseDTO.class,
                                        cinemaItem.cinemaItemId,
                                        cinemaItem.itemName,
                                        cinemaItem.itemDetail,
                                        cinemaItem.price,
                                        cinemaItem.sellType,
                                        cinemaItem.CinemaItemImageUrl
                                ))
                                ))).get(myCinemaId);

    }
}
