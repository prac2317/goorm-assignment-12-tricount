package com.example.tricount.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Settlement {

    private Long settlementNo;
    private String title;
    private List<Member> participants;
    private Balance balance;
    private List<Expense> expenses;

    public Settlement(String title, List<Member> participants, Balance balance) {
        this.title = title;
        this.participants = participants;
        this.balance = balance;
    }
}
