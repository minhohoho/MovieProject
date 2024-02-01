package com.example.movieproject.service;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.common.type.Role;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.dto.request.MovieCreateRequestDTO;
import com.example.movieproject.dto.request.MovieStaffCreateRequestDTO;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.Movie_StaffRepository;
import com.example.movieproject.repository.StaffRepository;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("영화 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks MovieService movieService;

    @Mock StaffRepository staffRepository;









}