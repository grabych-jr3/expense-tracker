package com.ogidazepam.expense_tracker.util.security;

import com.ogidazepam.expense_tracker.model.Expense;
import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.model.UserRole;
import com.ogidazepam.expense_tracker.repository.ExpenseRepository;
import com.ogidazepam.expense_tracker.repository.PersonRepository;
import com.ogidazepam.expense_tracker.service.security.CurrentUserService;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntitySecurity {

    private final PersonRepository personRepository;
    private final ExpenseRepository expenseRepository;
    private final CurrentUserService currentUserService;

    @Autowired
    public EntitySecurity(PersonRepository personRepository, ExpenseRepository expenseRepository, CurrentUserService currentUserService) {
        this.personRepository = personRepository;
        this.expenseRepository = expenseRepository;
        this.currentUserService = currentUserService;
    }

    public boolean isOwner(long id){

        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        return currentUserService.getCurrentPerson().getId() == expense.getOwner().getId();
    }

    public boolean isAdmin(long id){
        Person person = personRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        return person.getRole().equals(UserRole.ADMIN);
    }

    public boolean isTheSameUser(long id){
        Person person = personRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        return currentUserService.getCurrentPerson().getId() == person.getId();
    }
}
