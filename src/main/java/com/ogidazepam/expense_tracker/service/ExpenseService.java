package com.ogidazepam.expense_tracker.service;

import com.ogidazepam.expense_tracker.dto.expense.ExpenseCreatingDTO;
import com.ogidazepam.expense_tracker.dto.expense.ExpenseUpdatingDTO;
import com.ogidazepam.expense_tracker.dto.expense.ExpenseViewDTO;
import com.ogidazepam.expense_tracker.model.Category;
import com.ogidazepam.expense_tracker.model.Expense;
import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.repository.ExpenseRepository;
import com.ogidazepam.expense_tracker.util.security.CurrentUserService;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotCreatedException;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;

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

    @PreAuthorize("@expenseSecurity.isOwner(#id)")
    @Transactional
    public void updateExpense(long id, ExpenseUpdatingDTO dto) {
        Expense expense = expenseRepository
                .findById(id)
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

    public List<ExpenseViewDTO> findExpenses(String filter, String startDate, String endDate){
        Person person = currentUserService.getCurrentPerson();

        Instant start;
        Instant end = Instant.now();

        if(startDate != null && endDate != null){
            start = LocalDate.parse(startDate).atStartOfDay(ZoneOffset.UTC).toInstant();
            end = LocalDate.parse(endDate).atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toInstant();
        }else if(filter != null){
            int days = switch (filter){
                case "past_week" -> 7;
                case "past_month" -> 30;
                case "past_three_month" -> 90;
                default -> 30;
            };
            start = end.minus(Duration.ofDays(days));
        }else{
            start = Instant.EPOCH;
        }

        List<Expense> expenses = expenseRepository.findAllByOwnerIdAndCreatedAtBetweenOrderByCreatedAt(
                person.getId(),
                start,
                end
        );
        return convertToExpenseViewDTO(expenses);
    }

    @PreAuthorize("@expenseSecurity.isOwner(#id)")
    public ExpenseViewDTO findExpense(long id){
        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " not found"));

        return convertToExpenseViewDTO(expense);
    }

    private Expense convertToExpense(Object dto){
        return modelMapper.map(dto, Expense.class);
    }

    private List<ExpenseViewDTO> convertToExpenseViewDTO(List<Expense> expenses){
        return expenses.stream().map(expense -> modelMapper.map(expense, ExpenseViewDTO.class)).toList();
    }

    private ExpenseViewDTO convertToExpenseViewDTO(Expense expense){
        return modelMapper.map(expense, ExpenseViewDTO.class);
    }

    private Expense enrichExpense(Expense expense){
        expense.setCreatedAt(Instant.now());
        expense.setUpdatedAt(Instant.now());
        return expense;
    }
}
