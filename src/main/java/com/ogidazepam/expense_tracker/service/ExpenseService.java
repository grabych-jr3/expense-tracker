package com.ogidazepam.expense_tracker.service;

import com.ogidazepam.expense_tracker.dto.ExpenseCreatingDTO;
import com.ogidazepam.expense_tracker.dto.ExpenseUpdatingDTO;
import com.ogidazepam.expense_tracker.model.Category;
import com.ogidazepam.expense_tracker.model.Expense;
import com.ogidazepam.expense_tracker.repository.ExpenseRepository;
import com.ogidazepam.expense_tracker.util.exceptions.ExpenseNotCreatedException;
import com.ogidazepam.expense_tracker.util.exceptions.ExpenseNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;

@Service
@Transactional(readOnly = true)
public class ExpenseService {

    private final ModelMapper modelMapper;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ModelMapper modelMapper, ExpenseRepository expenseRepository) {
        this.modelMapper = modelMapper;
        this.expenseRepository = expenseRepository;
    }

    @Transactional
    public void saveExpense(ExpenseCreatingDTO dto){
        if(Arrays.stream(Category.values()).filter(c -> c.name().equals(dto.getCategory())).findFirst().isEmpty()){
            throw new ExpenseNotCreatedException("Category " + dto.getCategory() + " doesn't exist");
        }
        expenseRepository.save(enrichExpense(convertToExpense(dto)));
    }

    @Transactional
    public void updateExpense(ExpenseUpdatingDTO dto) {
        Expense expense = expenseRepository
                .findById(dto.getId())
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));

        expense.setPrice(dto.getPrice());
        expense.setCategory(dto.getCategory());
        expense.setUpdatedAt(Instant.now());
    }

    @Transactional
    public void deleteExpense(long id){
        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
        expenseRepository.delete(expense);
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
