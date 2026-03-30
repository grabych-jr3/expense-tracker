package com.ogidazepam.expense_tracker.controller;

import com.ogidazepam.expense_tracker.dto.forAdmin.ExpenseViewDTO;
import com.ogidazepam.expense_tracker.dto.forAdmin.PersonUpdateDTO;
import com.ogidazepam.expense_tracker.dto.forAdmin.PersonViewDTO;
import com.ogidazepam.expense_tracker.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<PersonViewDTO>> findAllUsers(){
        List<PersonViewDTO> people = adminService.findAllUsers();
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonViewDTO> findUserById(@PathVariable long id){
        PersonViewDTO dto = adminService.findById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public void updateUser(@PathVariable long id, @RequestBody PersonUpdateDTO dto){
        adminService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id){
        adminService.deleteUser(id);
    }

    @GetMapping("/{id}/expenses")
    public ResponseEntity<List<ExpenseViewDTO>> findAllExpenses(@PathVariable long id){
        List<ExpenseViewDTO> dtos = adminService.findAllExpensesByUserId(id);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}