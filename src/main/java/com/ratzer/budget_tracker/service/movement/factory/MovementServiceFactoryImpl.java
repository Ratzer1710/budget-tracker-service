package com.ratzer.budget_tracker.service.movement.factory;

import com.ratzer.budget_tracker.repository.MovementRepository;
import com.ratzer.budget_tracker.service.movement.MovementService;
import com.ratzer.budget_tracker.service.movement.MovementServiceImpl;
import com.ratzer.budget_tracker.service.movement.validator.MovementValidator;

public class MovementServiceFactoryImpl implements MovementServiceFactory {

    private final MovementRepository movementRepository;

    public MovementServiceFactoryImpl(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Override
    public MovementService getInstance() {
        return new MovementServiceImpl(this.movementRepository, new MovementValidator(this.movementRepository));
    }
}
