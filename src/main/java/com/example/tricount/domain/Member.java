package com.example.tricount.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Member {

    private Long userNo;

    private String userId;
    private String password;
    private String nickname;

    private List<Settlement> settlements;
    private List<Expense> expenses;

    public Member(String userId, String password, String nickname) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }
}
