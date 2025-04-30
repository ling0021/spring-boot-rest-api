package in.ling.restapi.service.impl;

import in.ling.restapi.dto.ExpenseDTO;
import in.ling.restapi.entity.ExpenseEntity;
import in.ling.restapi.exceptions.ResourceNotFoundException;
import in.ling.restapi.repository.ExpenseRepository;
import in.ling.restapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @description ExpenseServiceImpl implements the ExpenseService interface to manage expenses.
 * @author Ling
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    /**
     * @description This method fetches all expenses from the database.
     * @return List of ExpenseDTO objects containing expense details.
     **/
    @Override
    public List<ExpenseDTO> getAllExpenses() {
        // Call the repository method to fetch all expenses
        List<ExpenseEntity> list = expenseRepository.findAll();
        log.info("Print the data from repository {}", list);
        // Convert the Entity objects to DTOs using ModelMapper
        List<ExpenseDTO> listOfExpenses = list.stream().map(expenseEntity -> mapToExpenseDTO(expenseEntity)).collect(Collectors.toList());
        // Return the list of DTOs
        return listOfExpenses;
    }

    /**
     * @description This method fetches an expense by its ID from the database.
     * @param expenseId The ID of the expense to be fetched.
     * @return The ExpenseDTO object containing the expense details.
     **/
    @Override
    public ExpenseDTO  getExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = getExpenseEntity(expenseId);
        log.info("Print the expense entity details {}", expenseEntity);
        return mapToExpenseDTO(expenseEntity);
    }


    /**
     * @description This method deletes an expense by its ID from the database.
     * @param expenseId The ID of the expense to be deleted.
     **/
    @Override
    public void deleteExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = getExpenseEntity(expenseId);
        log.info("Print the expense entity details {}", expenseEntity);
        expenseRepository.delete(expenseEntity);
    }

    /**
     * @description This method saves expense details to the database.
     * @param expenseDTO The ExpenseDTO object containing expense details.
     * @return The saved ExpenseDTO object.
     **/
    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        ExpenseEntity newExpenseEntity = mapToExpenseEntity(expenseDTO);
        newExpenseEntity.setExpenseId(UUID.randomUUID().toString());
        newExpenseEntity = expenseRepository.save(newExpenseEntity);
        log.info("Print the expense entity details {}", newExpenseEntity);
        return mapToExpenseDTO(newExpenseEntity);
    }

    @Override
    public ExpenseDTO updateExpenseDetails(ExpenseDTO expenseDTO, String expenseId) {
        ExpenseEntity existingExpense = getExpenseEntity(expenseId);
        ExpenseEntity updatedExpenseEntity = mapToExpenseEntity(expenseDTO);
        updatedExpenseEntity.setId(existingExpense.getId());
        updatedExpenseEntity.setExpenseId(existingExpense.getExpenseId());
        updatedExpenseEntity.setCreatedAt(existingExpense.getCreatedAt());
        updatedExpenseEntity.setUpdatedAt(existingExpense.getUpdatedAt());
        updatedExpenseEntity = expenseRepository.save(updatedExpenseEntity);
        log.info("Print the expense entity details {}", updatedExpenseEntity);
        return mapToExpenseDTO(updatedExpenseEntity);
    }

    /**
     * @description This method maps an ExpenseDTO object to an ExpenseEntity object.
     * @param expenseDTO The ExpenseDTO object to be mapped.
     * @return The mapped ExpenseEntity object.
     **/
    private ExpenseEntity mapToExpenseEntity(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseEntity.class);
    }

    /**
     * @description This method maps an ExpenseEntity object to an ExpenseDTO object.
     * @param expenseEntity The ExpenseEntity object to be mapped.
     * @return The mapped ExpenseDTO object.
     **/
    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }

    /**
     * @description This method retrieves an ExpenseEntity object by its ID.
     * @param expenseId The ID of the expense to be retrieved.
     * @return The ExpenseEntity object.
     **/
    private ExpenseEntity getExpenseEntity(String expenseId) {
        return expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for the expense id" + expenseId));

    }
}
