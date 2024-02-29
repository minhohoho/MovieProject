package com.example.movieproject.service;

import com.example.movieproject.domain.Staff;
import com.example.movieproject.dto.request.StaffCreateRequestDTO;
import com.example.movieproject.dto.request.StaffUpdateRequestDTO;
import com.example.movieproject.dto.response.StaffUpdateResponseDTO;
import com.example.movieproject.repository.MovieStaffRepository;
import com.example.movieproject.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final MovieStaffRepository movieStaffRepository;

    @Transactional
    public Long createStaff(StaffCreateRequestDTO requestDTO){

        Staff staff = Staff.builder()
                .name(requestDTO.getName())
                .birth(requestDTO.getBirth())
                .nation(requestDTO.getNation())
                .build();

        return staffRepository.save(staff).getStaffId();
    }

    @Transactional
    public StaffUpdateResponseDTO updateStaffInfo(Long staffId, StaffUpdateRequestDTO updateDTO){

        Staff staff = staffRepository.findById(staffId).orElseThrow();

        staff.updateStaff(updateDTO);

        return StaffUpdateResponseDTO.entityToDTO(staff);
    }

    @Transactional
    public void deleteStaff(Long staffId){

        Staff staff = staffRepository.findById(staffId).orElseThrow();

        movieStaffRepository.deleteByStaff(staff);

        staffRepository.deleteById(staff.getStaffId());
    }






}
