package com.ratzer.budget_tracker.repository;

import com.ratzer.budget_tracker.repository.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, String> {
    @Query("SELECT b.name FROM Budget b")
    List<String> findAllNames();
}