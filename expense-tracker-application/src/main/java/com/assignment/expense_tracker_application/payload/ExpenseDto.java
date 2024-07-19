package com.assignment.expense_tracker_application.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {
    private long id;
    private String category;
    private double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
