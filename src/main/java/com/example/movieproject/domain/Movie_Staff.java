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
public class Movie_Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MovieStaffId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="movieId")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="staffId")
    private Staff staff;

    @Enumerated(EnumType.STRING)
    private Role role;
}
