package com.ratzer.budget_tracker.repository;

import com.ratzer.budget_tracker.repository.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    @Query("SELECT m FROM Movement m WHERE m.date >= :startDate AND m.date <= :endDate AND m.budget.name = :budgetName")
    List<Movement> findByDateBetween(
            @Param("budgetName") String budgetName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
