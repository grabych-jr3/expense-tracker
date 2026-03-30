package com.ogidazepam.expense_tracker.dto.forAdmin;

import com.ogidazepam.expense_tracker.model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public class ExpenseViewDTO {

    @NotNull(message = "price can't be null")
    @Positive(message = "price must be greater than 0")
    private Double price;

    private Category category;

    @NotNull(message = "Created at can't be empty")
    private Instant createdAt;

    @NotNull(message = "Updated at can't be empty")
    private Instant updatedAt;

    public ExpenseViewDTO(Double price, Category category, Instant createdAt, Instant updatedAt) {
        this.price = price;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ExpenseViewDTO() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
