package com.example.movieproject.domain;

import com.example.movieproject.common.type.SellType;
import com.example.movieproject.dto.request.CinemaItemUpdateRequestDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table
public class CinemaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinemaItemId")
    private Long cinemaItemId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="myCinemaId")
    @ToString.Exclude
    private MyCinema myCinema;

    private String itemName;

    private String itemDetail;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private SellType sellType;

    private String CinemaItemImageUrl;

    public void setImage(String imageUrl){
        this.CinemaItemImageUrl= imageUrl;
    }

    public void updateCinemaItemInfo(CinemaItemUpdateRequestDTO updateDTO){
        this.itemName=updateDTO.getItemName();
        this.itemDetail= updateDTO.getItemDetail();
        this.price=updateDTO.getPrice();
        this.sellType=updateDTO.getSellType();
    }




}
