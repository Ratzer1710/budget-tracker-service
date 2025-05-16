package com.ratzer.budget_tracker.config;

import com.ratzer.budget_tracker.repository.BudgetRepository;
import com.ratzer.budget_tracker.repository.MovementRepository;
import com.ratzer.budget_tracker.service.budget.factory.BudgetServiceFactory;
import com.ratzer.budget_tracker.service.budget.factory.BudgetServiceFactoryImpl;
import com.ratzer.budget_tracker.service.movement.factory.MovementServiceFactory;
import com.ratzer.budget_tracker.service.movement.factory.MovementServiceFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public MovementServiceFactory movementServiceFactory(MovementRepository movementRepository) {
        return new MovementServiceFactoryImpl(movementRepository);
    }

    @Bean
    public BudgetServiceFactory budgetServiceFactory(BudgetRepository budgetRepository, MovementServiceFactory movementServiceFactory) {
        return new BudgetServiceFactoryImpl(budgetRepository, movementServiceFactory);
    }

}
