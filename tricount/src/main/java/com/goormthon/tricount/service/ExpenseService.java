package com.goormthon.tricount.service;

import com.goormthon.tricount.TricountApiErrorCode;
import com.goormthon.tricount.model.Expense;
import com.goormthon.tricount.repository.ExpenseRepository;
import com.goormthon.tricount.repository.SettlementRepository;
import com.goormthon.tricount.utils.TricountApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final SettlementRepository settlementRepository;

    public Expense addNewExpense(Long settlementId, Expense expense) {
        if (expense.getExpenseDateTime().isEmpty()) {
            String dataTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            expense.setExpenseDateTime(dataTime);
        }
        settlementRepository.findSettlementMembers(settlementId).stream()
            .filter(s -> s.getId().equals(expense.getMemberId()))
            .findAny()
            .orElseThrow(() -> new TricountApiException("join member in settlement", TricountApiErrorCode.NOT_ACCEPTABLE));
        return expenseRepository.addExpense(settlementId, expense);
    }

    public List<Expense> findAllExpenses(Long settlementId) {
        return expenseRepository.findAllExpenses(settlementId);
    }

    public void deleteExpense(Long expenseId, Long settlementId) {
        expenseRepository.deleteExpense(expenseId, settlementId);
    }

}
