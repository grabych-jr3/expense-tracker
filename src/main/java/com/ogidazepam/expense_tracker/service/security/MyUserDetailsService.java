package com.ogidazepam.expense_tracker.service.security;

import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.repository.PersonRepository;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public MyUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Person person = personRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " is not found"));

        return User.builder()
                .username(person.getUsername())
                .password(person.getPassword())
                .roles(person.getRole().name())
                .build();
    }
}
