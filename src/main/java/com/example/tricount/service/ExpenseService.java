package com.example.tricount.service;

import com.example.tricount.domain.Expense;
import com.example.tricount.domain.Member;
import com.example.tricount.domain.Settlement;
import com.example.tricount.dto.request.ExpenseAddDto;
import com.example.tricount.dto.response.ExpenseGetDto;
import com.example.tricount.repository.ExpenseRepository;
import com.example.tricount.repository.MemberRepository;
import com.example.tricount.repository.SettlementRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;
    private final SettlementRepository settlementRepository;

    //Todo 전체 공통 전달받은 expenseNo, settlementNo 실제 존재하는지 확인 필요

    public ExpenseService(ExpenseRepository expenseRepository, MemberRepository memberRepository, SettlementRepository settlementRepository) {
        this.expenseRepository = expenseRepository;
        this.memberRepository = memberRepository;
        this.settlementRepository = settlementRepository;
    }

    // todo expenseAddDto에 있는 회원이 정산에 연결된 회원인지 확인 필요
    public Long addExpense(ExpenseAddDto expenseAddDto) {
        // expense로 변환
        Member member = memberRepository.findById(expenseAddDto.getUserNo()).get();
        Settlement settlement = settlementRepository.findById(expenseAddDto.getSettlementNo()).get();
        Expense expense = new Expense(expenseAddDto.getTitle(), member, expenseAddDto.getAmount(), expenseAddDto.getDate(), settlement);

        return expenseRepository.save(expense).getExpenseNo();
    }

    public ExpenseGetDto getExpense(Long expenseNo) {
        //1. Expense 꺼내오기
        Expense expense = expenseRepository.findById(expenseNo).get();
        //2. dto로 바꿔주기
        ExpenseGetDto expenseGetDto = convertToExpenseGetDto(expense);

        return expenseGetDto;
    }

    public List<ExpenseGetDto> getExpenseList(Long settlementNo) {
        List<Expense> expenseList = expenseRepository.findBySettlementNo(settlementNo);

        List<ExpenseGetDto> expenseGetDtoList = new ArrayList<>();
        for (Expense expense : expenseList) {
            expenseGetDtoList.add(convertToExpenseGetDto(expense));
        }

        return expenseGetDtoList;
    }


    private ExpenseGetDto convertToExpenseGetDto(Expense expense) {
        ExpenseGetDto expenseGetDto = new ExpenseGetDto();
        expenseGetDto.setExpenseNo(expense.getExpenseNo());
        expenseGetDto.setTitle(expense.getTitle());
        expenseGetDto.setDate(expense.getDate());
        expenseGetDto.setAmount(expense.getAmount());
        expenseGetDto.setPaidBy(expense.getPaidBy());
        expenseGetDto.setSettlement(expense.getSettlement());
        return expenseGetDto;
    }

}
