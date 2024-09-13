package com.example.tricount.controller;

import com.example.tricount.domain.Balance;
import com.example.tricount.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/balance}")
public class BalanceController {

    private final BalanceService balanceService;

//    @GetMapping("/{settlementNo}")
//    public ResponseEntity<Balance> getSettleBalance(
//            @PathVariable("settlementNo") Long settlementNo
//    ) {
//    }
}
