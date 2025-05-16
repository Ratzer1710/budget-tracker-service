package com.ratzer.budget_tracker.service.budget;

import com.ratzer.budget_tracker.exception.BadRequestException;
import com.ratzer.budget_tracker.exception.ConflictException;
import com.ratzer.budget_tracker.exception.NotFoundException;
import com.ratzer.budget_tracker.repository.entity.Budget;
import com.ratzer.budget_tracker.repository.entity.MonthlyBudget;
import com.ratzer.budget_tracker.repository.entity.Movement;

import java.time.LocalDate;
import java.util.List;

public interface BudgetService {
    MonthlyBudget createBudget(Budget budget) throws ConflictException;
    MonthlyBudget getBudget(String name, LocalDate from, LocalDate to) throws NotFoundException;
    void deleteBudget(String name) throws NotFoundException;
    List<String> getBudgetNames();

    Movement createMovement(String budgetName, Movement movement) throws NotFoundException, BadRequestException;
    List<Movement> getMovements(String budgetName, LocalDate from, LocalDate to) throws NotFoundException;
    void updateMovement(String budgetName, Long id, Movement updatedMovement) throws NotFoundException, BadRequestException;
    void deleteMovement(String budgetName, Long id) throws NotFoundException;
}
