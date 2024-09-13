package com.example.tricount.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
public class Balance {

//    private Long balanceNo;
    private List<Transfer> balances = new ArrayList<>();

    public Balance(List<Transfer> balances) {
        this.balances = balances;
    }
}
