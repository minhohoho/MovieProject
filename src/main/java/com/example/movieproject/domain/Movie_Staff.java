package com.example.movieproject.domain;

import com.example.movieproject.common.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie_Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MovieStaffId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn
    private Staff staff;

    @Enumerated(EnumType.STRING)
    private Role role;
}
