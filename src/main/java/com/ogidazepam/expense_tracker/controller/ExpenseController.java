package com.ogidazepam.expense_tracker.controller;

import com.ogidazepam.expense_tracker.dto.expense.ExpenseCreatingDTO;
import com.ogidazepam.expense_tracker.dto.expense.ExpenseUpdatingDTO;
import com.ogidazepam.expense_tracker.dto.expense.ExpenseViewDTO;
import com.ogidazepam.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
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

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseViewDTO> getExpense(@PathVariable long id){
        ExpenseViewDTO expenseViewDTO = expenseService.findExpense(id);
        return new ResponseEntity<>(expenseViewDTO, HttpStatus.OK);
    }

    @PostMapping
    public void saveNewExpense(@RequestBody @Valid ExpenseCreatingDTO dto){
        expenseService.saveExpense(dto);
    }

    @PatchMapping("/{id}")
    public void updateExpense(@RequestBody @Valid ExpenseUpdatingDTO dto,
                              @PathVariable long id){
        expenseService.updateExpense(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable long id){
        expenseService.deleteExpense(id);
    }

}
