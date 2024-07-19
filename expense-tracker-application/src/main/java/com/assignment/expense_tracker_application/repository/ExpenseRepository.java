package com.assignment.expense_tracker_application.repository;

import com.assignment.expense_tracker_application.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    @Query("SELECT e FROM Expense e ORDER BY e.updatedAt DESC")
    List<Expense> findAllByOrderByUpdatedAtDesc();
}
