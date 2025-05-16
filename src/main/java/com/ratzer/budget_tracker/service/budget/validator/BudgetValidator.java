package com.ratzer.budget_tracker.service.budget.validator;

import com.ratzer.budget_tracker.exception.ConflictException;
import com.ratzer.budget_tracker.exception.NotFoundException;
import com.ratzer.budget_tracker.repository.BudgetRepository;

public class BudgetValidator {

    private final BudgetRepository budgetRepository;

    public BudgetValidator(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public void checkBudgetConflict(String name) throws ConflictException {
        if (this.budgetRepository.existsById(name)) {
            throw new ConflictException("There is already a budget with that name.");
        }
    }

    public void checkBudgetExists(String name) throws NotFoundException {
        if (!this.budgetRepository.existsById(name)) {
            throw new NotFoundException("The requested budget was not found.");
        }
    }
}
