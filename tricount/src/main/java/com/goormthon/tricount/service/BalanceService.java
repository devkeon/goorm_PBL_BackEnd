package com.goormthon.tricount.service;

import com.goormthon.tricount.Tuple;
import com.goormthon.tricount.model.Balance;
import com.goormthon.tricount.model.Expense;
import com.goormthon.tricount.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceService {

    private final ExpenseRepository expenseRepository;

    public List<Balance> balances(Long settlementId) {

        List<Balance> result = new ArrayList<>();
        Map<String, Integer> expensePerMember = expenseRepository.findAllExpenses(settlementId)
                .stream()
                .collect(Collectors.toMap(Expense::getMemberName, Expense::getPrice, Integer::sum));

        int totalPrice = expensePerMember.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        List<Tuple> balancePerMember = new ArrayList<>();
        int perPrice = totalPrice / expensePerMember.size();
        expensePerMember.replaceAll((k, v) -> perPrice - v);

        for (Map.Entry<String, Integer> entry : expensePerMember.entrySet()){
            Tuple tuple = new Tuple(entry.getValue(), entry.getKey());
            if(tuple.getFirst() != 0){
                balancePerMember.add(tuple);
            }
        }
        Collections.sort(balancePerMember);

        int s = 0;
        int e = balancePerMember.size() - 1;

        Tuple start = balancePerMember.get(s);
        Tuple end = balancePerMember.get(e);
        while (s < e){
            int sum = start.getFirst() + end.getFirst();
            if (sum < 0){
                Balance balance = new Balance(end.getSecond(), end.getFirst(), start.getSecond());
                e -= 1;
                end = balancePerMember.get(e);
                result.add(balance);
            } else if (sum > 0){
                Balance balance = new Balance(end.getSecond(), Math.abs(start.getFirst()), start.getSecond());
                s += 1;
                start = balancePerMember.get(s);
                result.add(balance);
            } else {
                Balance balance = new Balance(end.getSecond(), end.getFirst(), start.getSecond());
                s += 1;
                e -= 1;
                result.add(balance);
            }
        }

        return result;
    }


}
