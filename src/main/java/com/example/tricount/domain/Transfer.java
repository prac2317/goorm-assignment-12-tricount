package com.example.tricount.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Transfer {

    private Long senderUserNo;
    private String senderUserName;
    private BigDecimal senderAmount;
    private Long receiverUserNo;
    private Long receiverUserName;
}
