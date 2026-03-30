package com.ogidazepam.expense_tracker.dto.expense;

import com.ogidazepam.expense_tracker.model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ExpenseUpdatingDTO {
    @NotNull(message = "Price can't be null")
    @Positive(message = "Price must be positive")
    private Double price;
    private Category category;

    public ExpenseUpdatingDTO() {
    }

    public ExpenseUpdatingDTO(Double price, Category category) {
        this.price = price;
        this.category = category;
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

    @Override
    public String toString() {
        return "ExpenseUpdatingDTO{" +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
