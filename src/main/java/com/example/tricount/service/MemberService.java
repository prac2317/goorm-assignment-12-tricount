package com.example.tricount.service;

import com.example.tricount.domain.Member;
import com.example.tricount.dto.request.MemberLoginRequestDto;
import com.example.tricount.dto.request.MemberSignupDto;
import com.example.tricount.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //todo 아이디 중복인지 확인하는 절차 필요함 - 회원 가입할 때
    public Member signup(MemberSignupDto memberSignupDto) {
        Member member = new Member();
        member.setUserId(memberSignupDto.getUserId());
        member.setPassword(memberSignupDto.getPassword());
        member.setNickname(memberSignupDto.getNickname());
        return memberRepository.save(member);
    }

    //todo 반환 타입으로 dto하려면 너무 복잡하니까 일단 member로 하기...
    public Member login(MemberLoginRequestDto memberLoginRequestDto) {
        return memberRepository.findByLoginId(memberLoginRequestDto.getUserId())
                .filter(m -> m.getPassword().equals(memberLoginRequestDto.getPassword()))
                .orElse(null);
    }
}
