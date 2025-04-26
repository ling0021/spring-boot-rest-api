package in.ling.restapi.service;

import in.ling.restapi.dto.ExpenseDTO;

import java.util.List;

/**
 * Service interface for managing expenses.
 * @author Ling
 */

public interface ExpenseService {

    /**
     * Fetches all expenses from database
     * @return List of ExpenseDTO objects containing expense details.
     */
    List<ExpenseDTO> getAllExpenses();

    /**
     * Fetches an expense by its ID from the database.
     * @param expenseId The ID of the expense to be fetched.
     * @return The ExpenseDTO object containing the expense details.
     */
    ExpenseDTO getExpenseByExpenseId(String expenseId);

    /**
     * Deletes an expense by its ID from the database.
     * @param expenseId The ID of the expense to be deleted.
     */
    void deleteExpenseByExpenseId(String expenseId);
}
