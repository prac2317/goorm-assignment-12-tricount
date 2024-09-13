package com.example.tricount.controller;

import com.example.tricount.domain.Settlement;
import com.example.tricount.dto.request.SettlementCreateDto;
import com.example.tricount.dto.response.SettlementGetDto;
import com.example.tricount.service.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settlement")
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping
    public ResponseEntity<List<SettlementGetDto>> getSettlementList(
            @RequestParam("userNo") Long userNo
    ) {
        List<SettlementGetDto> settlementGetDtoList = settlementService.getSettlementList(userNo);
        return new ResponseEntity<>(settlementGetDtoList, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<SettlementGetDto> createAndJoinSettlement(
            @RequestBody SettlementCreateDto settlementCreateDto
    ){
        Settlement settlement = settlementService.createSettlement(settlementCreateDto);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/api/settlement/get/" + settlement.getSettlementNo())
                .build();
    }

    @ResponseBody
    @GetMapping("/get/{settlementNo}")
    public ResponseEntity<SettlementGetDto> getSettlement(
            @PathVariable("settlementNo") Long settlementNo
    ) {
        SettlementGetDto settlementGetDto = settlementService.getSettlement(settlementNo);
        return new ResponseEntity<>(settlementGetDto, HttpStatus.OK);
    }

    // Todo 1. 일단 RequestParam으로 userNo도 함께 받자. 이후에 고치기 2. 그리고 삭제할 아이디가 있을지 없을지, 삭제 성공 여부 구분하기!!
    @DeleteMapping("/delete/{settlementNo}")
    public ResponseEntity<Void> deleteSettlement(
            @PathVariable("settlementNo") Long settlementNo,
            @RequestParam("userNo") Long userNo
    ) {
        settlementService.deleteSettlement(settlementNo);
        URI redirectUri = URI.create("/api/settlement?userNo=" + userNo);

        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }

    // todo 일단 여기도 requestParam으로 userNo 받기
    @PostMapping("/join/{settlementNo}")
    public ResponseEntity<Void> joinSettlement(
            @PathVariable("settlementNo") Long settlementNo,
            @RequestParam("userNo") Long userNo
    ){
        settlementService.joinSettlement(settlementNo, userNo);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/api/settlement/get/" + settlementNo)
                .build();
    }
}
