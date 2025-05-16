package com.ratzer.budget_tracker.service.movement;

import com.ratzer.budget_tracker.exception.BadRequestException;
import com.ratzer.budget_tracker.exception.NotFoundException;
import com.ratzer.budget_tracker.repository.MovementRepository;
import com.ratzer.budget_tracker.repository.entity.Movement;
import com.ratzer.budget_tracker.service.movement.validator.MovementValidator;

import java.time.LocalDate;
import java.util.List;

public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final MovementValidator movementValidator;

    public MovementServiceImpl(MovementRepository movementRepository, MovementValidator movementValidator) {
        this.movementRepository = movementRepository;
        this.movementValidator = movementValidator;
    }

    @Override
    public Movement createMovement(Movement movement, List<String> availableCategories) throws BadRequestException {
        this.movementValidator.validateMovementCategory(movement.getCategory(), availableCategories);
        return this.movementRepository.save(movement);
    }

    @Override
    public Movement getMovement(Long id) throws NotFoundException {
        return this.movementRepository.findById(id).orElseThrow(() -> new NotFoundException("The requested movement was not found."));
    }

    @Override
    public List<Movement> getMovements(String budgetName, LocalDate from, LocalDate to) {
        return this.movementRepository.findByDateBetween(budgetName, from, to);
    }

    @Override
    public void updateMovement(Long id, Movement updatedMovement, List<String> availableCategories) throws NotFoundException, BadRequestException {
        this.movementValidator.validateMovementExists(id);
        this.movementValidator.validateMovementCategory(updatedMovement.getCategory(), availableCategories);
        updatedMovement.setId(id);
        this.movementRepository.save(updatedMovement);
    }

    @Override
    public void deleteMovement(Long id) throws NotFoundException {
        this.movementValidator.validateMovementExists(id);
        this.movementRepository.deleteById(id);
    }
}
