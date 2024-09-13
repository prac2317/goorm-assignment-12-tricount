package com.example.tricount.dto.response;

import com.example.tricount.domain.Member;
import com.example.tricount.domain.Settlement;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseGetDto {

    private Long expenseNo;

    private String title;
    private Member paidBy;
    private BigDecimal amount;
    private LocalDate date;
    private Settlement settlement;

}
