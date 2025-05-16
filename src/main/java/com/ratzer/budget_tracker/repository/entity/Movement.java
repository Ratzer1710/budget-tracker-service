package com.ratzer.budget_tracker.repository.entity;

import jakarta.persistence.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "budget_name", referencedColumnName = "name", nullable = false)
    private Budget budget;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false, precision = 11, scale = 2)
    @Min(0)
    @Max(999999999)
    private BigDecimal value;

    @Column(nullable = false, precision = 11, scale = 2)
    @Min(0)
    @Max(999999999)
    private BigDecimal total;

    private String category;

    private String description;

    public Movement() {
        // No args constructor
    }

    public Movement(LocalDate date, Type type, BigDecimal value, String category, String description) {
        this.date = date;
        this.type = type;
        this.value = value;
        this.category = category;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return Objects.equals(id, movement.id) && Objects.equals(budget, movement.budget) && Objects.equals(date, movement.date) && type == movement.type && Objects.equals(value, movement.value) && Objects.equals(total, movement.total) && Objects.equals(category, movement.category) && Objects.equals(description, movement.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, budget, date, type, value, total, category, description);
    }

    @Override
    public String toString() {
        return "Movement{" +
                "id=" + id +
                ", budget=" + budget +
                ", date=" + date +
                ", type=" + type +
                ", value=" + value +
                ", total=" + total +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}