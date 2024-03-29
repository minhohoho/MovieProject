package com.example.movieproject.domain;

import com.example.movieproject.dto.request.ReviewUpdateRequestDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reviewId")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="memberId")
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="movieId")
    @ToString.Exclude
    private Movie movie;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Double score;

    @ColumnDefault("0")
    private int reviewCnt;

    public void updateReview(ReviewUpdateRequestDTO updateDTO){
        this.content = updateDTO.getContent();
        this.score   = updateDTO.getScore();
    }

    public void ChangeReviewCnt(int reviewCnt){
        this.reviewCnt = reviewCnt;
    }



}
