package com.example.movieproject.dto.response;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class YearIncomeResponseDTO {


    private int year;
    private int month;
    private int week;
    private int totalIncome;

    public static YearIncomeResponseDTO YearIncome(int year,int totalIncome){
        return YearIncomeResponseDTO.builder()
                .year(year)
                .totalIncome(totalIncome)
                .build();
    }

    public static YearIncomeResponseDTO YearAndMonthIncome(int year,int month,int totalIncome){
        return YearIncomeResponseDTO.builder()
                .year(year)
                .month(month)
                .totalIncome(totalIncome)
                .build();
    }

    public static YearIncomeResponseDTO YearAndMonthAndWeekIncome(int year,int month,int week,int totalIncome){
        return YearIncomeResponseDTO.builder()
                .year(year)
                .month(month)
                .week(1)
                .totalIncome(totalIncome)
                .build();
    }

}
