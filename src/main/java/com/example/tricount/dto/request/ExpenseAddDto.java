package com.example.tricount.dto.request;

import com.example.tricount.domain.Member;
import com.example.tricount.domain.Settlement;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseAddDto {

    private String title;
    private BigDecimal amount;
    private LocalDate date;
    private Long userNo;
    private Long settlementNo;
}
