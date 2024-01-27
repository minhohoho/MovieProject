package com.example.movieproject.domain;

import com.example.movieproject.repository.StaffRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(SpringExtension.class)
public class StaffJpaTest {

    @Autowired
    private StaffRepository staffRepository;

    @DisplayName("스태프 입력 테스트")
    @Test
    void GivenStaff_WhenInserting_ThenWorksFine(){

        //given
        String name = "하정우";

        //when
        Staff staff = staffRepository.save(Staff.builder()
                .name(name)
                .build());

        //then
        assertEquals(1,staff.getStaffId());
        assertEquals(name,staff.getName());
    }






}
