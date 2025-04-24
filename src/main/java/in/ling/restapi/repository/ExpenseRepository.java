package in.ling.restapi.repository;

import in.ling.restapi.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing ExpenseEntity objects.
 * This interface extends JpaRepository to provide CRUD operations.
 * @author Ling
 */

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    /**
     * Find an expense by its ID.
     * @param expenseId the ID of the expense
     * @return an Optional containing the found ExpenseEntity, or empty if not found
     */
    Optional<ExpenseEntity> findByExpenseId(String expenseId);
}
