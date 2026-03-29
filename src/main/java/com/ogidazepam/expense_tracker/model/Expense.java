package com.ogidazepam.expense_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

@Entity
@Table(name = "Expense")
public class Expense {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "price can't be null")
    @Positive(message = "price must be greater than 0")
    @Column(name = "price")
    private Double price;

    @Column(name = "category", nullable = false)
    private Category category;

    @NotNull(message = "Created at can't be empty")
    @Column(name = "created_At")
    private Instant createdAt;

    @NotNull(message = "Updated at can't be empty")
    @Column(name = "updated_At")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person owner;

    public Expense() {
    }

    public Expense(Double price, Category category, Instant createdAt, Instant updatedAt) {
        this.price = price;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", price=" + price +
                ", category=" + category +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
