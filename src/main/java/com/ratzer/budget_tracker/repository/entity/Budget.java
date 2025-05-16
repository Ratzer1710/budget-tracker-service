package com.ratzer.budget_tracker.repository.entity;

import jakarta.persistence.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false, precision = 11, scale = 2)
    @Min(0)
    @Max(999999999)
    private BigDecimal balance;

    @ElementCollection
    @CollectionTable(name = "budget_categories", joinColumns = @JoinColumn(name = "budget_name"))
    @Column(name = "category")
    private List<String> categories;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movement> movements = new ArrayList<>();

    public Budget() {
        // No args constructor
    }

    public Budget(String name, Currency currency, BigDecimal balance, List<String> categories) {
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return Objects.equals(name, budget.name) && currency == budget.currency && Objects.equals(balance, budget.balance) && Objects.equals(categories, budget.categories) && Objects.equals(movements, budget.movements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, currency, balance, categories, movements);
    }

    @Override
    public String toString() {
        return "Budget{" +
                "name='" + name + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
                ", categories=" + categories +
                ", movements=" + movements +
                '}';
    }
}
