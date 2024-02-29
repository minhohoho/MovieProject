package com.example.movieproject.service;

import com.example.movieproject.repository.MovieStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieStaffService {

    private final MovieStaffRepository movieStaffRepository;

}
