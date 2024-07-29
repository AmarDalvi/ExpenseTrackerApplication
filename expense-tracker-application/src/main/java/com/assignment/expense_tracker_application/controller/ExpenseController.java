package com.assignment.expense_tracker_application.controller;

import com.assignment.expense_tracker_application.entity.Expense;
import com.assignment.expense_tracker_application.payload.ExpenseDto;
import com.assignment.expense_tracker_application.payload.ExpenseResponse;
import com.assignment.expense_tracker_application.service.ExpenseService;
import com.assignment.expense_tracker_application.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping()
    public ResponseEntity<ExpenseDto>createExpense(@Valid @RequestBody ExpenseDto expenseDto){
        return new ResponseEntity<>(expenseService.createExpense(expenseDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ExpenseResponse getAllExpenses(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ){
        return expenseService.getAllExpenses(pageNo, pageSize,sortBy,sortDir);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto>getExpenseById(@PathVariable(name="id")long id){
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto>updateExpenseById(@Valid @RequestBody ExpenseDto expenseDto, @PathVariable(name="id")long id){
        return new ResponseEntity<>(expenseService.updateExpenseById(expenseDto,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePostById(@PathVariable(name="id")long id){
        expenseService.deleteExpenseById(id);
        return new ResponseEntity<>("Expense Deleted Successfully",HttpStatus.OK);
    }
}
