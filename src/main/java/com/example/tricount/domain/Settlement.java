package com.example.tricount.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Settlement {

    private Long settlementNo;
    private String title;
    private List<Member> participants = new ArrayList<>();
    private Balance balance;
//    private List<Expense> expenses = new ArrayList<>();

    public Settlement(String title) {
        this.title = title;
    }
}
