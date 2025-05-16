package com.ratzer.budget_tracker.repository.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class MonthlyBudget {
    private String name;
    private Currency currency;
    private BigDecimal balance;
    private BigDecimal income;
    private BigDecimal expenses;
    private List<String> categories;

    public MonthlyBudget() {
        // No args constructor
    }

    public MonthlyBudget(Budget budget, LocalDate fromDate, LocalDate toDate) {
        this.name = budget.getName();
        this.currency = budget.getCurrency();
        this.balance = budget.getBalance();
        this.categories = budget.getCategories();

        this.income = budget.getMovements().stream()
                .filter(m -> !m.getDate().isBefore(fromDate) && !m.getDate().isAfter(toDate) && m.getType() == Type.INCOME)
                .map(Movement::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.expenses = budget.getMovements().stream()
                .filter(m -> !m.getDate().isBefore(fromDate) && !m.getDate().isAfter(toDate) && m.getType() == Type.EXPENSE)
                .map(Movement::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.income = this.income.setScale(2, RoundingMode.HALF_UP);
        this.expenses = this.expenses.setScale(2, RoundingMode.HALF_UP);
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

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyBudget that = (MonthlyBudget) o;
        return Objects.equals(name, that.name) && currency == that.currency && Objects.equals(balance, that.balance) && Objects.equals(income, that.income) && Objects.equals(expenses, that.expenses) && Objects.equals(categories, that.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, currency, balance, income, expenses, categories);
    }

    @Override
    public String toString() {
        return "MonthlyBudget{" +
                "name='" + name + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
                ", income=" + income +
                ", expenses=" + expenses +
                ", categories=" + categories +
                '}';
    }
}
