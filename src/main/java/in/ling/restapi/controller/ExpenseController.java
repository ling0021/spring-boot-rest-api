package in.ling.restapi.controller;

import in.ling.restapi.dto.ExpenseDTO;
import in.ling.restapi.io.ExpenseRequest;
import in.ling.restapi.io.ExpenseResponse;
import in.ling.restapi.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @description ExpenseController handles HTTP requests related to expenses.
 * @author Ling
 */
@RestController
@RequiredArgsConstructor
@Slf4j
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
     * @description This method handles DELETE requests to delete an expense by its ID.
     * @param expenseId The ID of the expense to be deleted.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses/{expenseId}")
    public void deleteExpenseByExpenseId(@PathVariable String expenseId) {
        log.info("API DELETE /expenses/{} called", expenseId);
        expenseService.deleteExpenseByExpenseId(expenseId);
    }

    /**
     * @description This method handles POST requests to save expense details.
     * @param expenseRequest The request object containing expense details.
     * @return The saved ExpenseResponse object.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
        log.info("API POST /expenses called {}", expenseRequest);
        ExpenseDTO expenseDTO = mapToExpenseDTO(expenseRequest);
        expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
        log.info("Print the expense dto {}", expenseDTO);
        return mapToExpenseResponse(expenseDTO);
    }


    /**
     * @description This method handles PUT requests to update expense details.
     * @param updateRequest The request object containing updated expense details.
     * @param expenseId The ID of the expense to be updated.
     * @return The updated ExpenseResponse object.
     */
    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest updateRequest, @PathVariable String expenseId) {
        log.info("API PUT /expenses/{} request body {}", expenseId, updateRequest);
        ExpenseDTO updatedExpenseDTO = mapToExpenseDTO(updateRequest);
        updatedExpenseDTO = expenseService.updateExpenseDetails(updatedExpenseDTO, expenseId);
        log.info("Printing the updated expense dto details {}", updatedExpenseDTO);
        return mapToExpenseResponse(updatedExpenseDTO);
    }

    /**
     * @description This method maps an ExpenseRequest object to an ExpenseDTO object.
     * @param expenseRequest The ExpenseRequest object to be mapped.
     * @return The mapped ExpenseDTO object.
     */
    private ExpenseDTO mapToExpenseDTO(@Valid ExpenseRequest expenseRequest) {
        return modelMapper.map(expenseRequest, ExpenseDTO.class);
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
