package com.ogidazepam.expense_tracker.service;

import com.ogidazepam.expense_tracker.dto.person.PersonRegisterDTO;
import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.model.UserRole;
import com.ogidazepam.expense_tracker.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(ModelMapper modelMapper, PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    @Transactional
    public void saveUser(PersonRegisterDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        personRepository.save(enrichPerson(convertToPerson(dto)));
    }

    private Person convertToPerson(Object dto){
        return modelMapper.map(dto, Person.class);
    }

    private Person enrichPerson(Person person){
        person.setRole(UserRole.USER);
        person.setCreatedAt(Instant.now());
        person.setUpdatedAt(Instant.now());
        return person;
    }
}
