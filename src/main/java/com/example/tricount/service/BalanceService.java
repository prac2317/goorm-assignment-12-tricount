package com.example.tricount.service;

import com.example.tricount.domain.*;
import com.example.tricount.repository.ExpenseRepository;
import com.example.tricount.repository.SettlementRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BalanceService {

    private final SettlementRepository settlementRepository;
    private final ExpenseRepository expenseRepository;

    public BalanceService(SettlementRepository settlementRepository, ExpenseRepository expenseRepository) {
        this.settlementRepository = settlementRepository;
        this.expenseRepository = expenseRepository;
    }

    public void getSettleBalance(Long settlementNo) {
        List<Expense> expenseList = expenseRepository.findBySettlementNo(settlementNo);
        List<Member> participants = settlementRepository.findById(settlementNo).get().getParticipants();

        // 금액 평균 구하기
        BigDecimal total = BigDecimal.ZERO;
        for (Expense expense : expenseList) {
            BigDecimal amount = expense.getAmount();
            total = total.add(amount);
        }
        BigDecimal average = total.divide(new BigDecimal(expenseList.size()), 0, RoundingMode.HALF_UP);

        // 참가자 별 지출과 평균 차이
        BigDecimal[] memberAmountList = new BigDecimal[participants.size()];
        for (int i = 0; i < memberAmountList.length; i++) {
            memberAmountList[i] = BigDecimal.ZERO;
            for (int j = 0; j < expenseList.size(); j++) {
                if (expenseList.get(j).getPaidBy().getUserNo() == participants.get(i).getUserNo()) {
                    memberAmountList[i] = memberAmountList[i].add(expenseList.get(j).getAmount());
                }
            }
            memberAmountList[i] = memberAmountList[i].subtract(average);
        }

        // 전송 금액 계산하기
        for (int i = 0; i < memberAmountList.length; i++) {
            if (memberAmountList[i].equals(BigDecimal.ZERO)) continue;

            for (int j = i; j < memberAmountList.length; j++) {
                if (memberAmountList[i].signum() == memberAmountList[j].signum()) continue;

                int comparison = memberAmountList[i].abs().compareTo(memberAmountList[j].abs());
                if (comparison > 0) {

                }
            }
        }
    }

}
