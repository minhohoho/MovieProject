package com.example.movieproject.service;

import com.example.movieproject.domain.Staff;
import com.example.movieproject.dto.request.StaffCreateRequestDTO;
import com.example.movieproject.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    @Transactional
    public Long createStaff(StaffCreateRequestDTO requestDTO){

        Staff staff = Staff.builder()
                .name(requestDTO.getName())
                .birth(requestDTO.getBirth())
                .nation(requestDTO.getNation())
                .build();

        return staffRepository.save(staff).getStaffId();
    }




}
