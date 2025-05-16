package com.ratzer.budget_tracker.rest.budget;

import com.ratzer.budget_tracker.api.BudgetsApiDelegate;
import com.ratzer.budget_tracker.api.model.Budget;
import com.ratzer.budget_tracker.api.model.BudgetDto;
import com.ratzer.budget_tracker.api.model.Movement;
import com.ratzer.budget_tracker.api.model.MovementDto;
import com.ratzer.budget_tracker.repository.entity.MonthlyBudget;
import com.ratzer.budget_tracker.rest.budget.mapper.BudgetMapper;
import com.ratzer.budget_tracker.rest.budget.mapper.MovementMapper;
import com.ratzer.budget_tracker.service.budget.BudgetService;
import com.ratzer.budget_tracker.service.budget.factory.BudgetServiceFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BudgetDelegate implements BudgetsApiDelegate {

    private final BudgetService budgetService;
    private final BudgetMapper budgetMapper;
    private final MovementMapper movementMapper;

    public BudgetDelegate(BudgetServiceFactory budgetServiceFactory) {
        this.budgetService = budgetServiceFactory.getInstance();
        this.budgetMapper = new BudgetMapper();
        this.movementMapper = new MovementMapper();
    }

    @Override
    public ResponseEntity<Budget> createBudget(BudgetDto budgetDto) throws Exception {
        com.ratzer.budget_tracker.repository.entity.Budget budgetToCreate = this.budgetMapper.mapBudget(budgetDto);

        MonthlyBudget createdBudget = this.budgetService.createBudget(budgetToCreate);

        Budget budgetApi = this.budgetMapper.mapBudgetApi(createdBudget);

        return ResponseEntity.status(201).body(budgetApi);
    }

    @Override
    public ResponseEntity<Void> deleteBudget(String name) throws Exception {
        this.budgetService.deleteBudget(name);

        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<Budget> getBudget(String name, LocalDate from, LocalDate to) throws Exception {
        MonthlyBudget budget = this.budgetService.getBudget(name, from, to);

        Budget budgetApi = this.budgetMapper.mapBudgetApi(budget);

        return ResponseEntity.status(200).body(budgetApi);
    }

    @Override
    public ResponseEntity<List<String>> getBudgets() {
        List<String> budgets = this.budgetService.getBudgetNames();

        return ResponseEntity.status(200).body(budgets);
    }

    @Override
    public ResponseEntity<Movement> createMovement(String name, MovementDto movementDto) throws Exception {
        com.ratzer.budget_tracker.repository.entity.Movement movement = this.movementMapper.mapMovementDto(movementDto);

        movement = this.budgetService.createMovement(name, movement);

        Movement movementApi = this.movementMapper.mapMovementApi(movement);

        return ResponseEntity.status(201).body(movementApi);
    }

    @Override
    public ResponseEntity<List<Movement>> getMovements(String name, LocalDate from, LocalDate to) throws Exception {
        List<com.ratzer.budget_tracker.repository.entity.Movement> movements = this.budgetService.getMovements(name, from, to);

        List<Movement> movementsApi = this.movementMapper.mapMovementsApi(movements);

        return ResponseEntity.status(200).body(movementsApi);
    }

    @Override
    public ResponseEntity<Void> updateMovement(String name, Long id, MovementDto movementDto) throws Exception {
        com.ratzer.budget_tracker.repository.entity.Movement movement = this.movementMapper.mapMovementDto(movementDto);

        this.budgetService.updateMovement(name, id, movement);

        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<Void> deleteMovement(String name, Long id) throws Exception {
        this.budgetService.deleteMovement(name, id);

        return ResponseEntity.status(204).build();
    }
}
