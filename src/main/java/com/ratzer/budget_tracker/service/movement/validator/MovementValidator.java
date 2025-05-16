package com.ratzer.budget_tracker.service.movement.validator;

import com.ratzer.budget_tracker.exception.BadRequestException;
import com.ratzer.budget_tracker.exception.NotFoundException;
import com.ratzer.budget_tracker.repository.MovementRepository;

import java.util.List;

public class MovementValidator {

    private final MovementRepository movementRepository;

    public MovementValidator(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public void validateMovementExists(Long id) throws NotFoundException {
        if (!this.movementRepository.existsById(id)) {
            throw new NotFoundException("The movement does not exist");
        }
    }

    public void validateMovementCategory(String category, List<String> budgetCategories) throws BadRequestException {
        if (!budgetCategories.contains(category)) {
            throw new BadRequestException("The provided category is not allowed within this budget");
        }
    }
}
