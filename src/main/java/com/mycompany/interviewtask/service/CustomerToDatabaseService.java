package com.mycompany.interviewtask.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mycompany.interviewtask.entity.Customer;

import java.io.FileNotFoundException;
import java.util.List;

public interface CustomerToDatabaseService {
    List<Customer> writeCustomers() throws JsonMappingException, FileNotFoundException;

}
