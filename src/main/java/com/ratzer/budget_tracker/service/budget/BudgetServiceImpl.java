package com.ratzer.budget_tracker.service.budget;

import com.ratzer.budget_tracker.exception.BadRequestException;
import com.ratzer.budget_tracker.exception.ConflictException;
import com.ratzer.budget_tracker.exception.NotFoundException;
import com.ratzer.budget_tracker.repository.BudgetRepository;
import com.ratzer.budget_tracker.repository.entity.Budget;
import com.ratzer.budget_tracker.repository.entity.MonthlyBudget;
import com.ratzer.budget_tracker.repository.entity.Movement;
import com.ratzer.budget_tracker.repository.entity.Type;
import com.ratzer.budget_tracker.service.budget.validator.BudgetValidator;
import com.ratzer.budget_tracker.service.movement.MovementService;
import com.ratzer.budget_tracker.service.movement.factory.MovementServiceFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetValidator validator;
    private final MovementService movementService;

    public BudgetServiceImpl(BudgetRepository budgetRepository, BudgetValidator validator, MovementServiceFactory movementServiceFactory) {
        this.budgetRepository = budgetRepository;
        this.validator = validator;
        this.movementService = movementServiceFactory.getInstance();
    }

    @Override
    public MonthlyBudget createBudget(Budget budget) throws ConflictException {
        this.validator.checkBudgetConflict(budget.getName());
        budget = this.budgetRepository.save(budget);
        return new MonthlyBudget(budget, LocalDate.now(), LocalDate.now());
    }

    @Override
    public MonthlyBudget getBudget(String name, LocalDate from, LocalDate to) throws NotFoundException {
        Budget budget = this.budgetRepository.findById(name)
                .orElseThrow(() -> new NotFoundException("The requested budget was not found."));
        return new MonthlyBudget(budget, from, to);
    }

    @Override
    public void deleteBudget(String name) throws NotFoundException {
        this.validator.checkBudgetExists(name);
        this.budgetRepository.deleteById(name);
    }

    @Override
    public List<String> getBudgetNames() {
        return this.budgetRepository.findAllNames();
    }

    private Budget getBudget(String budget) throws NotFoundException {
        return this.budgetRepository.findById(budget)
                .orElseThrow(() -> new NotFoundException("The requested budget was not found."));
    }

    private BigDecimal adjustTotal(BigDecimal balance, BigDecimal value, Type type, boolean isRevert) {
        return switch (type) {
            case INCOME -> isRevert ? balance.subtract(value) : balance.add(value);
            case EXPENSE -> isRevert ? balance.add(value) : balance.subtract(value);
        };
    }

    @Override
    public Movement createMovement(String budgetName, Movement movement) throws NotFoundException, BadRequestException {
        Budget budget = getBudget(budgetName);

        budget.setBalance(adjustTotal(budget.getBalance(), movement.getValue(), movement.getType(), false));

        movement.setTotal(budget.getBalance());
        movement.setBudget(budget);

        movement = this.movementService.createMovement(movement, budget.getCategories());

        this.budgetRepository.save(budget);

        return movement;
    }

    @Override
    public List<Movement> getMovements(String budgetName, LocalDate from, LocalDate to) throws NotFoundException {
        this.validator.checkBudgetExists(budgetName);

        return this.movementService.getMovements(budgetName, from, to);
    }

    @Override
    public void updateMovement(String budgetName, Long id, Movement updatedMovement) throws NotFoundException, BadRequestException {
        Budget budget = getBudget(budgetName);
        Movement oldMovement = this.movementService.getMovement(id);

        if (!oldMovement.getType().equals(updatedMovement.getType()) || oldMovement.getValue().compareTo(updatedMovement.getValue()) != 0) {
            BigDecimal initialBalance = adjustTotal(budget.getBalance(), oldMovement.getValue(), oldMovement.getType(), true);
            budget.setBalance(adjustTotal(initialBalance, updatedMovement.getValue(), updatedMovement.getType(), false));
            this.budgetRepository.save(budget);
        }

        updatedMovement.setTotal(budget.getBalance());
        updatedMovement.setBudget(budget);

        this.movementService.updateMovement(id, updatedMovement, budget.getCategories());
    }

    @Override
    public void deleteMovement(String budgetName, Long id) throws NotFoundException {
        Budget budget = getBudget(budgetName);
        Movement movement = this.movementService.getMovement(id);

        budget.setBalance(adjustTotal(budget.getBalance(), movement.getValue(), movement.getType(), true));
        this.budgetRepository.save(budget);

        this.movementService.deleteMovement(id);
    }
}
