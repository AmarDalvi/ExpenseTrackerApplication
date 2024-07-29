package com.assignment.expense_tracker_application.service;

import com.assignment.expense_tracker_application.payload.ExpenseDto;
import com.assignment.expense_tracker_application.payload.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    ExpenseDto createExpense(ExpenseDto expenseDto);

    ExpenseResponse getAllExpenses(int pageNo, int pageSize, String sortBy, String sortDir);

//    List<ExpenseDto>getAllExpenses();

    ExpenseDto getExpenseById(long expenseId);

    ExpenseDto updateExpenseById(ExpenseDto expenseDto,long expenseId);

    void deleteExpenseById(long id);

}
