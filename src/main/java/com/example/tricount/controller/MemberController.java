package com.example.tricount.controller;

import com.example.tricount.domain.Member;
import com.example.tricount.dto.request.MemberLoginRequestDto;
import com.example.tricount.dto.request.MemberSignupDto;
import com.example.tricount.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    // todo 응답에 비밀번호 있으면 안될듯.. 고치기
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody MemberSignupDto memberSignupDto
    ) {
        Member savedMember = memberService.signup(memberSignupDto);
        return ResponseEntity.status(201).body(savedMember);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody MemberLoginRequestDto memberLoginRequestDto,
            HttpServletRequest request
    ) {
        Member loginMember = memberService.login(memberLoginRequestDto);

        if (loginMember == null) {
            return ResponseEntity.status(401).body("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        return ResponseEntity.ok("로그인 완료");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 완료");
    }
}
