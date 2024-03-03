package com.example.movieproject.domain;


import com.example.movieproject.dto.kakaoApi.KakakoApiResponseDTO;
import com.example.movieproject.dto.request.MyCinemaUpdateRequestDTO;
import lombok.*;


import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Getter
@Table(indexes = {
        @Index(columnList = "createdAt")
})
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

    public void updateMyCinema(MyCinemaUpdateRequestDTO updateDTO, KakakoApiResponseDTO kakakoApiResponseDTO){
        this.cinemaName= updateDTO.getCinemaName();
        this.cinemaDetail= updateDTO.getCinemaDetail();
        this.cinemaItem= updateDTO.getCinemaItem();
        this.addressName= updateDTO.getAddressName();
        this.latitude = kakakoApiResponseDTO.getDocumentList().get(0).getLatitude();
        this.longitude= kakakoApiResponseDTO.getDocumentList().get(0).getLongitude();
    }



}
