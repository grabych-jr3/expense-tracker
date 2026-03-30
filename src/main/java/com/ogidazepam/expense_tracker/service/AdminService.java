package com.ogidazepam.expense_tracker.service;

import com.ogidazepam.expense_tracker.dto.forAdmin.ExpenseViewDTO;
import com.ogidazepam.expense_tracker.dto.forAdmin.PersonUpdateDTO;
import com.ogidazepam.expense_tracker.dto.forAdmin.PersonViewDTO;
import com.ogidazepam.expense_tracker.model.Expense;
import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.model.UserRole;
import com.ogidazepam.expense_tracker.repository.PersonRepository;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
@PreAuthorize("hasRole('ADMIN')")
public class AdminService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    @Autowired
    public AdminService(ModelMapper modelMapper, PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    public List<PersonViewDTO> findAllUsers() {
        List<Person> people = personRepository.findAllByRole(UserRole.USER);
        return convertToPersonViewDtoList(people);
    }

    @PreAuthorize("!@entitySecurity.isTargetUserAdmin(#id) or entitySecurity.isTheSameUser(#id)")
    public PersonViewDTO findById(long id){
        Person person = personRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return convertToPersonViewDto(person);
    }

    @PreAuthorize("!@entitySecurity.isTargetUserAdmin(#id)")
    @Transactional
    public void updateUser(long id, PersonUpdateDTO dto){
        Person personToBeUpdated = personRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        personToBeUpdated.setUsername(dto.getUsername());
        personToBeUpdated.setPassword(passwordEncoder.encode(dto.getPassword()));
        personToBeUpdated.setUpdatedAt(Instant.now());
    }

    @PreAuthorize("!@entitySecurity.isTargetUserAdmin(#id)")
    @Transactional
    public void deleteUser(long id){
        Person personToBeDeleted = personRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        personRepository.delete(personToBeDeleted);
    }

    @PreAuthorize("!@entitySecurity.isTargetUserAdmin(#id) or @entitySecurity.isTheSameUser(#id)")
    public List<ExpenseViewDTO> findAllExpensesByUserId(long id){
        Person person = personRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        return convertToExpenseViewDtoList(person.getExpenses());
    }



    private PersonViewDTO convertToPersonViewDto(Person person){
        return modelMapper.map(person, PersonViewDTO.class);
    }

    private List<PersonViewDTO> convertToPersonViewDtoList(List<Person> people){
        return people.stream().map(p -> modelMapper.map(p, PersonViewDTO.class)).toList();
    }

    private List<ExpenseViewDTO> convertToExpenseViewDtoList(List<Expense> expenses){
        return expenses.stream().map(expense -> modelMapper.map(expense, ExpenseViewDTO.class)).toList();
    }
}
