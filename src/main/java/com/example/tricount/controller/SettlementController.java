package com.example.tricount.controller;

import com.example.tricount.domain.Settlement;
import com.example.tricount.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settlement")
public class SettlementController {

    private final SettlementService settlementService;

//    @GetMapping("/api/settlement")
//    public List<Settlement> getSettlementList(@RequestParam("userNo") Long userNo) {
//        settlementService.getSettlementList
//    }
}
