package com.assignment.expense_tracker_application.service;

import com.assignment.expense_tracker_application.payload.LoginDto;
import com.assignment.expense_tracker_application.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
