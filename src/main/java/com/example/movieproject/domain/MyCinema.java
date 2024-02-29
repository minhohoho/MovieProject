package com.example.movieproject.domain;


import lombok.*;


import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Getter
@Table
public class MyCinema extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myCinemaId")
    private Long myCinemaId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="memberId")
    @ToString.Exclude
    private Member member;

    @Column(nullable = false)
    private String cinemaName;

    @Column(nullable = false)
    private String cinemaDetail;

     @Column(nullable = false)
     private String cinemaItem;

    @Column(nullable = false)
    private String addressName;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;
}
