package in.ling.restapi.controller;

import in.ling.restapi.dto.ExpenseDTO;
import in.ling.restapi.io.ExpenseResponse;
import in.ling.restapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @description ExpenseController handles HTTP requests related to expenses.
 * @author Ling
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    /**
     * @description This method handles GET requests to fetch all expenses.
     * @return List of ExpenseResponse objects containing expense details.
     */
    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpense() {
        log.info("API GET /expenses called");
        // Call the service method to fetch all expenses
        List<ExpenseDTO> list = expenseService.getAllExpenses();
        log.info("Print the data from service {}", list);
        // Convert the list of ExpenseDTOs to a list of ExpenseResponse objects using ModelMapper
        List<ExpenseResponse> response =  list.stream().map(expenseDTO -> mapToExpenseResponse(expenseDTO)).collect(Collectors.toList());
        // Return the list of ExpenseResponse objects
        return response;
    }

    /**
     * @description This method handles GET requests to fetch an expense by its ID.
     * @param expenseId The ID of the expense to be fetched.
     * @return The ExpenseResponse object containing the expense details.
     */

    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpenseId(@PathVariable String expenseId) {
        log.info("API GET /expenses/{} called", expenseId);
        ExpenseDTO expenseDTO = expenseService.getExpenseByExpenseId(expenseId);
        log.info("Print the expense details {}", expenseDTO);
        return mapToExpenseResponse(expenseDTO);
    }

    /**
     * @description This method maps an ExpenseDTO object to an ExpenseResponse object.
     * @param expenseDTO The ExpenseDTO object to be mapped.
     * @return The mapped ExpenseResponse object.
     */
    private ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseResponse.class);
    }
}
