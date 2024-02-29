package com.example.movieproject.domain;

import com.example.movieproject.dto.request.StaffUpdateRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@ToString
@Table()
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="staffId")
    private Long StaffId;

    @Column(nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date birth;

    @Column(nullable = false)
    private String nation;

    public void updateStaff(StaffUpdateRequestDTO updateDTO){
    this.name =updateDTO.getName();
    this.birth=updateDTO.getBirth();
    this.nation=updateDTO.getNation();
    }



}
