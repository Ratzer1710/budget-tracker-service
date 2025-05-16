package com.ratzer.budget_tracker.service.movement;

import com.ratzer.budget_tracker.exception.BadRequestException;
import com.ratzer.budget_tracker.exception.NotFoundException;
import com.ratzer.budget_tracker.repository.entity.Movement;

import java.time.LocalDate;
import java.util.List;

public interface MovementService {
    Movement createMovement(Movement movement, List<String> availableCategories) throws BadRequestException;
    Movement getMovement(Long id) throws NotFoundException;
    List<Movement> getMovements(String budgetName, LocalDate from, LocalDate to);
    void updateMovement(Long id, Movement updatedMovement, List<String> availableCategories) throws NotFoundException, BadRequestException;
    void deleteMovement(Long id) throws NotFoundException;
}
