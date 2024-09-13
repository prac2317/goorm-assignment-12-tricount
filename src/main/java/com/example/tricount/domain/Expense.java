package com.example.tricount.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Expense {

    private Long expenseNo;

    private String title;
    private Member paidBy;
    private BigDecimal amount;
    private LocalDate date;
    private Settlement settlement;

//    public Expense(String title, BigDecimal amount, LocalDate date) {
//        this.title = title;
//        this.amount = amount;
//        this.date = date;
//    }

    public Expense(String title, Member paidBy, BigDecimal amount, LocalDate date, Settlement settlement) {
        this.title = title;
        this.paidBy = paidBy;
        this.amount = amount;
        this.date = date;
        this.settlement = settlement;
    }
}
