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
}
