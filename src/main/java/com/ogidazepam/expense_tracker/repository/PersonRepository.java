package com.ogidazepam.expense_tracker.repository;

import com.ogidazepam.expense_tracker.model.Person;
import com.ogidazepam.expense_tracker.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
    List<Person> findAllByRole(UserRole role);
}
