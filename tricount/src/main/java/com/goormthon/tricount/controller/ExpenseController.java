package com.goormthon.tricount.controller;

import com.goormthon.tricount.model.ApiResponse;
import com.goormthon.tricount.model.Balance;
import com.goormthon.tricount.model.Expense;
import com.goormthon.tricount.service.BalanceService;
import com.goormthon.tricount.service.ExpenseService;
import com.goormthon.tricount.utils.TricountApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/settle")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final BalanceService balanceService;

    @GetMapping("/{settleNo}/expenses")
    public ApiResponse<Expense> findAllExpenses(@PathVariable Long settleNo) {
        List<Expense> expenses = expenseService.findAllExpenses(settleNo);
        return new ApiResponse<Expense>().ok(expenses);
    }

    @PostMapping("/{settleNo}/expenses/add")
    public ApiResponse<Expense> addNewExpense(@PathVariable Long settleNo,
                                                      @Validated @RequestBody Expense expense) {
        try {
            Expense added = expenseService.addNewExpense(settleNo, expense);
            return new ApiResponse<Expense>().ok(added);
        } catch (TricountApiException e) {
            return new ApiResponse().fail(e.getErrorCode().getCode(), e.getMessage());
        }
    }

    @DeleteMapping("/{settleNo}/expenses/{expenseNo}")
    public ApiResponse deleteExpense(@PathVariable Long settleNo, @PathVariable Long expenseNo) {
        expenseService.deleteExpense(expenseNo, settleNo);
        return new ApiResponse().ok();
    }

    @GetMapping("/{settleNo}/balance")
    public ApiResponse<Balance> getBalance(@PathVariable Long settleNo) {
        List<Balance> balances = balanceService.balances(settleNo);
        return new ApiResponse().ok(balances);
    }
}
