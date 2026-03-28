package com.ogidazepam.expense_tracker.dto;

import com.ogidazepam.expense_tracker.model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ExpenseCreatingDTO{

    @NotNull(message = "Price can't be null")
    @Positive(message = "Price must be positive")
    private Double price;

    private String category;

    public ExpenseCreatingDTO() {
    }

    public ExpenseCreatingDTO(Double price, String category) {
        this.price = price;
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ExpenseCreatingDTO{" +
                "price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
