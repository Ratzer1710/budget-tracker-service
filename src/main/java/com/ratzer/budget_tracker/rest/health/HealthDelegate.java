package com.ratzer.budget_tracker.rest.health;

import com.ratzer.budget_tracker.api.HealthApiDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class HealthDelegate implements HealthApiDelegate {

    private final DataSource dataSource;

    public HealthDelegate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Method that checks weather Budget Tracker Service and its database are working properly
     *
     * @return Successful response entity (204 NO_CONTENT) if Budget Tracker Service and its database are working properly or an unsuccessful response entity when they are not.
     */
    @Override
    public ResponseEntity<Void> healthCheck() throws SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        connection.isValid(2);

        return ResponseEntity.status(204).build();
    }
}
