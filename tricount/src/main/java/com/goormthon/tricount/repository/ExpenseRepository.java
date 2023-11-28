package com.goormthon.tricount.repository;

import com.goormthon.tricount.model.Expense;

import java.util.List;

public interface ExpenseRepository {
    public List<Expense> findAllExpenses(Long settlementId);

    public Expense addExpense(Long settlementId, Expense expense);

    public void deleteExpense(Long expenseId, Long settlementId);

    public void clear();

}
