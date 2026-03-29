package com.ogidazepam.expense_tracker.repository;

import com.ogidazepam.expense_tracker.model.Expense;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByOwnerIdAndCreatedAtBetweenOrderByCreatedAt(long owner_id, Instant startDate, Instant endDate);
}
