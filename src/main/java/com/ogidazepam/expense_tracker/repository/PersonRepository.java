package com.ogidazepam.expense_tracker.repository;

import com.ogidazepam.expense_tracker.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
