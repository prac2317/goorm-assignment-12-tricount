package com.example.tricount.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignupDto {

    private String userId;
    private String password;
    private String nickname;

}
