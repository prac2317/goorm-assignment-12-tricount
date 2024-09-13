package com.example.tricount.controller;

import com.example.tricount.dto.request.ExpenseAddDto;
import com.example.tricount.dto.response.ExpenseGetDto;
import com.example.tricount.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/{settlementNo}/add")
    public ResponseEntity<Void> addExpense(
            @RequestBody ExpenseAddDto expenseAddDto,
            @PathVariable("settlementNo") Long settlementNo
    ) {
        expenseService.addExpense(expenseAddDto);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/api/expense/" + settlementNo)
                .build();
    }

    @GetMapping("/{settlementNo}")
    public ResponseEntity<List<ExpenseGetDto>> getExpenseList(
            @PathVariable("settlementNo") Long settlementNo
    ) {
        List<ExpenseGetDto> expenseGetDtoList = expenseService.getExpenseList(settlementNo);
        return new ResponseEntity<>(expenseGetDtoList, HttpStatus.OK);
    }

    @GetMapping("/{settlementNo}/{expenseNo}")
    public ResponseEntity<ExpenseGetDto> getExpense(
            @PathVariable("settlementNo") Long settlementNo,
            @PathVariable("expenseNo") Long expenseNo
    ) {
        ExpenseGetDto expenseGetDto = expenseService.getExpense(expenseNo);
        return new ResponseEntity<>(expenseGetDto, HttpStatus.OK);
    }
}
