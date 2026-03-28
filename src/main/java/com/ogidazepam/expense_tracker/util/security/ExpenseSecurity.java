package com.ogidazepam.expense_tracker.util.security;

import com.ogidazepam.expense_tracker.model.Expense;
import com.ogidazepam.expense_tracker.repository.ExpenseRepository;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseSecurity {

    private final ExpenseRepository expenseRepository;
    private final CurrentUserService currentUserService;

    @Autowired
    public ExpenseSecurity(ExpenseRepository expenseRepository, CurrentUserService currentUserService) {
        this.expenseRepository = expenseRepository;
        this.currentUserService = currentUserService;
    }

    public boolean isOwner(long id){

        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        return currentUserService.getCurrentPerson().getId() == expense.getOwner().getId();
    }
}
