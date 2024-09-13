package com.example.tricount.service;

import com.example.tricount.domain.Expense;
import com.example.tricount.domain.Member;
import com.example.tricount.domain.Settlement;
import com.example.tricount.dto.request.SettlementCreateDto;
import com.example.tricount.dto.request.SettlementCreateMemberDto;
import com.example.tricount.dto.response.SettlementGetDto;
import com.example.tricount.dto.response.SettlementGetExpenseDto;
import com.example.tricount.dto.response.SettlementGetMemberDto;
import com.example.tricount.repository.MemberRepository;
import com.example.tricount.repository.SettlementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SettlementService {

    // todo 전달받은 key값들 실제로 있는지 확인

    private final SettlementRepository settlementRepository;
    private final MemberRepository memberRepository;

    public SettlementService(SettlementRepository settlementRepository, MemberRepository memberRepository) {
        this.settlementRepository = settlementRepository;
        this.memberRepository = memberRepository;
    }

    public List<SettlementGetDto> getSettlementList(Long userNo) {
        List<Settlement> settlementList = settlementRepository.findByUserNo(userNo);

        List<SettlementGetDto> settlementGetDtoList = new ArrayList<>();
        for (Settlement settlement : settlementList) {
            SettlementGetDto settlementGetDto = convertToSettlementGetDto(settlement);
            settlementGetDtoList.add(settlementGetDto);
        }

        return settlementGetDtoList;

        //dto로 변환하는 작업 필요하다..!
    }

    //todo 만드는 과정에서 실제 멤버가 있는지 체크하기!
    public Settlement createSettlement(SettlementCreateDto settlementCreateDto) {

        Settlement settlement = new Settlement(settlementCreateDto.getTitle());

        // Member 변환
        ArrayList<Member> participants = new ArrayList<>();
        for (SettlementCreateMemberDto participantDto : settlementCreateDto.getParticipantsDto()) {
            Member member = memberRepository.findById(participantDto.getUserNo()).get();
            participants.add(member);
        }

        settlement.setParticipants(participants);
        return settlementRepository.save(settlement);
    }


    public SettlementGetDto getSettlement(Long settlementNo) {
        Settlement settlement = settlementRepository.findById(settlementNo).get();
        SettlementGetDto settlementGetDto = convertToSettlementGetDto(settlement);
        return settlementGetDto;
    }

    public void deleteSettlement(Long settlementNo) {
        settlementRepository.delete(settlementNo);
    }

    public void joinSettlement(Long SettlementNo, Long userNo) {
        Settlement settlement = settlementRepository.findById(SettlementNo).get();
        Member member = memberRepository.findById(userNo).get();
        settlementRepository.join(settlement, member);
    }

    private SettlementGetDto convertToSettlementGetDto(Settlement settlement) {
        SettlementGetDto settlementGetDto = new SettlementGetDto();
        settlementGetDto.setSettlementNo(settlement.getSettlementNo());
        settlementGetDto.setTitle(settlement.getTitle());

        // Participants 변환
        List<SettlementGetMemberDto> participants = new ArrayList<>();
        for (Member member : settlement.getParticipants()) {
            SettlementGetMemberDto participantDto = new SettlementGetMemberDto();
            participantDto.setUserNo(member.getUserNo());
            participantDto.setUserId(member.getUserId());
            participants.add(participantDto);
        }
        settlementGetDto.setParticipants(participants);

        // Expenses 변환
//        List<SettlementGetExpenseDto> expenses = new ArrayList<>();
//        for (Expense expense : settlement.getExpenses()) {
//            SettlementGetExpenseDto expenseDto = new SettlementGetExpenseDto();
//            expenseDto.setTitle(expense.getTitle());
//            expenseDto.setPaidBy(expense.getPaidBy());
//            expenseDto.setAmount(expense.getAmount());
//            expenseDto.setDate(expense.getDate());
//            expenses.add(expenseDto);
//        }
//        settlementGetDto.setExpenses(expenses);
        return settlementGetDto;
    }
}
