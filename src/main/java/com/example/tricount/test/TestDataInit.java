package com.example.tricount.test;

import com.example.tricount.domain.Member;
import com.example.tricount.domain.Settlement;
import com.example.tricount.repository.MemberRepository;
import com.example.tricount.repository.SettlementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// 연습용으로 만들어 본 것.. 나중에 지우기!

@Slf4j
@Component
public class TestDataInit {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        memberRepository.save(new Member("userId1", "password1", "nickname1"));
        memberRepository.save(new Member("userId2", "password2", "nickname2"));
        settlementRepository.save(new Settlement("title1"));
        settlementRepository.save(new Settlement("title2"));
    }
}
