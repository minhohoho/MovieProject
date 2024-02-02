package com.example.movieproject.domain;

import com.example.movieproject.common.type.Role;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Getter
@Table
public class Movie_Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MovieStaffId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="movieId")
    @ToString.Exclude
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="staffId")
    @ToString.Exclude
    @Setter
    private Staff staff;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private Role role;




}
