package com.example.tricount.dto.response;

import com.example.tricount.domain.Balance;
import com.example.tricount.domain.Expense;
import com.example.tricount.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettlementGetDto {

    private Long settlementNo;

    private String title;
    private List<SettlementGetMemberDto> participants;
    private List<SettlementGetExpenseDto> expenses;

}
