package com.example.movieproject.domain;

import lombok.*;

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
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="movieId")
    @ToString.Exclude
    private Movie MovieId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Double score;
}
