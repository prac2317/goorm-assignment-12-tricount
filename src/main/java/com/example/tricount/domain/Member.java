package com.example.tricount.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {

    private Long userNo;

    private String userId;
    private String password;
    private String nickname;

    private Settlement settlement;
    private Expense expense;

    public Member(String userId, String password, String nickname) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }
}
