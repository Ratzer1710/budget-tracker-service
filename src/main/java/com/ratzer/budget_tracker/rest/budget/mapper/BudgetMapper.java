package com.ratzer.budget_tracker.rest.budget.mapper;

import com.ratzer.budget_tracker.api.model.BudgetDto;
import com.ratzer.budget_tracker.repository.entity.Budget;
import com.ratzer.budget_tracker.repository.entity.Currency;
import com.ratzer.budget_tracker.repository.entity.MonthlyBudget;
import java.math.RoundingMode;

public class BudgetMapper {

    public Budget mapBudget(BudgetDto budgetDto) {
        return new Budget(
                budgetDto.getName(),
                Currency.valueOf(budgetDto.getCurrency().name()),
                budgetDto.getBalance().setScale(2, RoundingMode.HALF_UP),
                budgetDto.getCategories()
        );
    }

    public com.ratzer.budget_tracker.api.model.Budget mapBudgetApi(MonthlyBudget monthlyBudget) {
        return new com.ratzer.budget_tracker.api.model.Budget()
                .name(monthlyBudget.getName())
                .currency(com.ratzer.budget_tracker.api.model.Currency.valueOf(monthlyBudget.getCurrency().name()))
                .balance(monthlyBudget.getBalance())
                .income(monthlyBudget.getIncome())
                .expenses(monthlyBudget.getExpenses())
                .categories(monthlyBudget.getCategories());
    }
}
