package in.ling.restapi.service.impl;

import in.ling.restapi.dto.ExpenseDTO;
import in.ling.restapi.entity.ExpenseEntity;
import in.ling.restapi.repository.ExpenseRepository;
import in.ling.restapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
        List<ExpenseEntity> list =  expenseRepository.findAll();
        log.info("Print the data from repository {}", list);
        // Convert the Entity objects to DTOs using ModelMapper
        List<ExpenseDTO> listOfExpenses = list.stream().map(expenseEntity -> mapToExpenseDTO(expenseEntity)).collect(Collectors.toList());
        // Return the list of DTOs
        return listOfExpenses;
    }

    /**
     * @description This method maps an ExpenseEntity object to an ExpenseDTO object.
     * @param expenseEntity The ExpenseEntity object to be mapped.
     * @return The mapped ExpenseDTO object.
     **/
    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }
}
