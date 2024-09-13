package com.example.tricount.test;

import com.example.tricount.domain.Expense;
import com.example.tricount.domain.Member;
import com.example.tricount.domain.Settlement;
import com.example.tricount.repository.ExpenseRepository;
import com.example.tricount.repository.MemberRepository;
import com.example.tricount.repository.SettlementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

// 연습용으로 만들어 본 것.. 나중에 지우기!

@Slf4j
@Component
public class TestDataInit {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
//        Member member1 = memberRepository.save(new Member("member1", "password1", "nickname1"));
//        Member member2 = memberRepository.save(new Member("member2", "password2", "nickname2"));
//        Member member3 = memberRepository.save(new Member("member3", "password3", "nickname3"));
//        Member member4 = memberRepository.save(new Member("member4", "password4", "nickname4"));
//        Member member5 = memberRepository.save(new Member("member5", "password5", "nickname5"));
//        Member member6 = memberRepository.save(new Member("member6", "password6", "nickname6"));
//        Settlement settlement1 = settlementRepository.save(new Settlement("settlement1"));
//        Settlement settlement2 = settlementRepository.save(new Settlement("settlement2"));
//        settlementRepository.join(settlement1, member1);
//        settlementRepository.join(settlement1, member2);
//        settlementRepository.join(settlement1, member3);
//        settlementRepository.join(settlement1, member4);
//        expenseRepository.save(new Expense("expense1", member1, new BigDecimal("10000"), LocalDate.now(), settlement1));
//        expenseRepository.save(new Expense("expense2", member2, new BigDecimal("20000"), LocalDate.now(), settlement1));
//        expenseRepository.save(new Expense("expense2", member4, new BigDecimal("40000"), LocalDate.now(), settlement1));
    }
}
