package com.ogidazepam.expense_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

@Entity
@Table(name = "Expense")
public class Expense {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "price can't be empty")
    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @NotBlank(message = "Date can't be empty")
    @Column(name = "date")
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person owner;

    public Expense() {
    }

    public Expense(double price, Category category, Instant date) {
        this.price = price;
        this.category = category;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
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
                ", date=" + date +
                '}';
    }
}
