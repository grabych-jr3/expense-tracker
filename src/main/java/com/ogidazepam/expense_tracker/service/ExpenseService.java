package com.ogidazepam.expense_tracker.service;

import com.ogidazepam.expense_tracker.dto.expense.ExpenseCreatingDTO;
import com.ogidazepam.expense_tracker.dto.expense.ExpenseUpdatingDTO;
import com.ogidazepam.expense_tracker.model.Category;
import com.ogidazepam.expense_tracker.model.Expense;
import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.repository.ExpenseRepository;
import com.ogidazepam.expense_tracker.repository.PersonRepository;
import com.ogidazepam.expense_tracker.util.security.CurrentUserService;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotCreatedException;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotFoundException;
import com.ogidazepam.expense_tracker.util.security.ExpenseSecurity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional(readOnly = true)
public class ExpenseService {

    private final ModelMapper modelMapper;
    private final ExpenseRepository expenseRepository;
    private final CurrentUserService currentUserService;

    @Autowired
    public ExpenseService(ModelMapper modelMapper, CurrentUserService currentUserService, ExpenseRepository expenseRepository) {
        this.modelMapper = modelMapper;
        this.expenseRepository = expenseRepository;
        this.currentUserService = currentUserService;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Transactional
    public void saveExpense(ExpenseCreatingDTO dto){
        try{
            Category.valueOf(dto.getCategory());
        }catch(IllegalArgumentException e){
            throw new EntityNotCreatedException("Category " + dto.getCategory() + " doesn't exist");
        }

        Person person = currentUserService.getCurrentPerson();
        Expense expense = convertToExpense(dto);
        expense.setOwner(person);
        person.getExpenses().add(expense);

        expenseRepository.save(enrichExpense(expense));
    }

    @PreAuthorize("@expenseSecurity.isOwner(#dto.id)")
    @Transactional
    public void updateExpense(ExpenseUpdatingDTO dto) {
        Expense expense = expenseRepository
                .findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));
        expense.setPrice(dto.getPrice());
        expense.setCategory(dto.getCategory());
        expense.setUpdatedAt(Instant.now());
    }

    @PreAuthorize("@expenseSecurity.isOwner(#id)")
    @Transactional
    public void deleteExpense(long id){
        expenseRepository.deleteById(id);
    }

    private Expense convertToExpense(Object dto){
        return modelMapper.map(dto, Expense.class);
    }

    private Expense enrichExpense(Expense expense){
        expense.setCreatedAt(Instant.now());
        expense.setUpdatedAt(Instant.now());
        return expense;
    }
}
