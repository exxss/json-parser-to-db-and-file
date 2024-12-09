package com.mycompany.interviewtask.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mycompany.interviewtask.constants.PathConstants;
import com.mycompany.interviewtask.entity.Customer;
import com.mycompany.interviewtask.repository.CustomerRepository;
import com.mycompany.interviewtask.service.CustomerToDatabaseService;
import com.mycompany.interviewtask.utils.MyJsonReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceToDatabaseImpl implements CustomerToDatabaseService {

    private final CustomerRepository customerRepository;
    private final MyJsonReader myJsonReader;

    @Override
    @PostConstruct
    public List<Customer> writeCustomers() throws JsonMappingException, FileNotFoundException {
        List<Customer> customers = myJsonReader.getListCustomersFromJsonFile(PathConstants.PATH_OF_JSON_FILE);
        customers.forEach(s->s.setRating(calcRating(s.getNumberOfPurchases(),s.getNumberOfReturns(),s.getStatus().getStatusAsNumb())));
        return customerRepository.saveAll(customers);
    }

    private Integer calcRating(Integer numberOfPurchases, Integer numberOfReturns, Integer statusAsNumb){
        return numberOfPurchases - numberOfReturns + statusAsNumb;
    }
}
