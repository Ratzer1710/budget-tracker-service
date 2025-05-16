package com.ratzer.budget_tracker.service.budget.factory;

import com.ratzer.budget_tracker.repository.BudgetRepository;
import com.ratzer.budget_tracker.service.budget.BudgetService;
import com.ratzer.budget_tracker.service.budget.BudgetServiceImpl;
import com.ratzer.budget_tracker.service.budget.validator.BudgetValidator;
import com.ratzer.budget_tracker.service.movement.factory.MovementServiceFactory;

public class BudgetServiceFactoryImpl implements BudgetServiceFactory {

    private final BudgetRepository budgetRepository;
    private final MovementServiceFactory movementServiceFactory;

    public BudgetServiceFactoryImpl(BudgetRepository budgetRepository, MovementServiceFactory movementServiceFactory) {
        this.budgetRepository = budgetRepository;
        this.movementServiceFactory = movementServiceFactory;
    }

    @Override
    public BudgetService getInstance() {
        return new BudgetServiceImpl(this.budgetRepository, new BudgetValidator(budgetRepository), this.movementServiceFactory);
    }
}
