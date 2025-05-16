package com.ratzer.budget_tracker.rest.budget.mapper;

import com.ratzer.budget_tracker.api.model.MovementDto;
import com.ratzer.budget_tracker.repository.entity.Movement;
import com.ratzer.budget_tracker.repository.entity.Type;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MovementMapper {

    public Movement mapMovementDto(MovementDto movementDto) {
        return new Movement(
                movementDto.getDate(),
                Type.valueOf(movementDto.getType().name()),
                movementDto.getValue().setScale(2, RoundingMode.HALF_UP),
                movementDto.getCategory(),
                movementDto.getDescription());
    }

    public com.ratzer.budget_tracker.api.model.Movement mapMovementApi(Movement movement) {
        return new com.ratzer.budget_tracker.api.model.Movement()
                .id(movement.getId())
                .date(movement.getDate())
                .type(com.ratzer.budget_tracker.api.model.Type.valueOf(movement.getType().name()))
                .value(movement.getValue().setScale(2, RoundingMode.HALF_UP))
                .total(movement.getTotal().setScale(2, RoundingMode.HALF_UP))
                .category(movement.getCategory())
                .description(movement.getDescription());
    }

    public List<com.ratzer.budget_tracker.api.model.Movement> mapMovementsApi(List<Movement> movements) {
        List<com.ratzer.budget_tracker.api.model.Movement> movementsApi = new ArrayList<>();

        for (Movement movement : movements) {
            movementsApi.add(mapMovementApi(movement));
        }

        return movementsApi;
    }

    public Type mapType(com.ratzer.budget_tracker.api.model.Type type) {
        try {
            return Type.valueOf(type.name());
        } catch (Exception e) {
            return null;
        }
    }
}
