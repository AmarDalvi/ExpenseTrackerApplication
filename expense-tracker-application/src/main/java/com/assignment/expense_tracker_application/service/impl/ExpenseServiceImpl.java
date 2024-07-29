package com.assignment.expense_tracker_application.service.impl;

import com.assignment.expense_tracker_application.entity.Expense;
import com.assignment.expense_tracker_application.entity.User;
import com.assignment.expense_tracker_application.exception.ExpenseTrackerAPIException;
import com.assignment.expense_tracker_application.exception.ResourceNotFoundException;
import com.assignment.expense_tracker_application.payload.ExpenseDto;
import com.assignment.expense_tracker_application.payload.ExpenseResponse;
import com.assignment.expense_tracker_application.repository.ExpenseRepository;
import com.assignment.expense_tracker_application.repository.UserRepository;
import com.assignment.expense_tracker_application.service.ExpenseService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private ModelMapper mapper;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ModelMapper mapper) {
        this.expenseRepository = expenseRepository;
        this.mapper = mapper;
    }

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {

        Expense expense = mapToEntity(expenseDto);

//        if (expense.getCreatedAt() == null) {
//            expense.setCreatedAt(LocalDateTime.now());
//        }
        Expense newExpense = expenseRepository.save(expense);
        return mapToDTO(newExpense);
    }



//    @Override
//    public List<ExpenseDto> getAllExpenses() {
//        List<Expense> expenses =  expenseRepository.findAllByOrderByUpdatedAtDesc();
//        return expenses.stream().map(expense -> mapToDTO(expense)).collect(Collectors.toList());
//    }

    @Override
    public ExpenseResponse getAllExpenses(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by("updatedAt");

        if (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        // create Pageable instance
        Pageable pageable =  PageRequest.of(pageNo,pageSize,sort/*.descending for desc*/);

        Page<Expense> expenses = expenseRepository.findAll(pageable);
        // get content for page object
        List<Expense> listofExpenses = expenses.getContent();

        List<ExpenseDto> content = listofExpenses.stream().map(expense -> mapToDTO(expense)).collect(Collectors.toList());

        ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setContent(content);
        expenseResponse.setPageNo(expenses.getNumber());
        expenseResponse.setPageSize(expenses.getSize());
        expenseResponse.setTotalElements(expenses.getTotalElements());
        expenseResponse.setTotalPages(expenses.getTotalPages());
        expenseResponse.setLast(expenses.isLast());

        return expenseResponse;
    }

    @Override
    public ExpenseDto getExpenseById(long expenseId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(()->
                new ResourceNotFoundException("Expense","id",expenseId));
        return mapToDTO(expense);
    }

    @Override
    public ExpenseDto updateExpenseById(ExpenseDto expenseDto, long expenseId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(()-> new ResourceNotFoundException("Expense","id",expenseId));

        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(expenseDto.getCategory());

        Expense updatedExpense = expenseRepository.save(expense);
        return mapToDTO(updatedExpense);
    }

    @Override
    public void deleteExpenseById(long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Expense","id",id));
        expenseRepository.delete(expense);
    }

    private ExpenseDto mapToDTO(Expense expense){
        //used ModelMapper here
        ExpenseDto expenseDto = mapper.map(expense,ExpenseDto.class);
        return expenseDto;
    }

    //convert DTO to entity
    private Expense mapToEntity(ExpenseDto expenseDto){
        //Used ModelMapper here
        Expense expense = mapper.map(expenseDto,Expense.class);
        return expense;
    }
}
