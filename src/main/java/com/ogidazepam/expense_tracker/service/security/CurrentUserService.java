package com.ogidazepam.expense_tracker.service.security;

import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.repository.PersonRepository;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

    private final PersonRepository personRepository;

    @Autowired
    public CurrentUserService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getCurrentPerson(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated())
            throw new IllegalStateException("No authenticated user");

        String username = authentication.getName();

        return personRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found"));
    }
}
