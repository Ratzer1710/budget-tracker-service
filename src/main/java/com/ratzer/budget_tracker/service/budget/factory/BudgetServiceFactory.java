package com.ratzer.budget_tracker.service.budget.factory;

import com.ratzer.budget_tracker.service.budget.BudgetService;

public interface BudgetServiceFactory {
    BudgetService getInstance();
}
