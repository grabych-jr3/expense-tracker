package com.ogidazepam.expense_tracker.controller;

import com.ogidazepam.expense_tracker.dto.expense.ExpenseCreatingDTO;
import com.ogidazepam.expense_tracker.dto.expense.ExpenseUpdatingDTO;
import com.ogidazepam.expense_tracker.dto.expense.ExpenseViewDTO;
import com.ogidazepam.expense_tracker.model.Expense;
import com.ogidazepam.expense_tracker.service.ExpenseService;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotCreatedException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseViewDTO>> getAllExpenses(
        @RequestParam(required = false) String filter,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate
    ){
        List<ExpenseViewDTO> expenses = expenseService.findExpenses(filter, startDate, endDate);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PostMapping
    public void saveNewExpense(@RequestBody @Valid ExpenseCreatingDTO dto,
                               BindingResult bindingResult){
        getAllFieldErrors(bindingResult);
        expenseService.saveExpense(dto);
    }

    @PatchMapping("/{id}")
    public void updateExpense(@RequestBody @Valid ExpenseUpdatingDTO dto,
                              BindingResult bindingResult,
                              @PathVariable long id){
        getAllFieldErrors(bindingResult);
        expenseService.updateExpense(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable long id){
        expenseService.deleteExpense(id);
    }

    private void getAllFieldErrors(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsgs = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for(FieldError error : errors){
                errorMsgs.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotCreatedException(errorMsgs.toString());
        }
    }
}
