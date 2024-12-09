package com.mycompany.interviewtask.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mycompany.interviewtask.constants.PathConstants;
import com.mycompany.interviewtask.entity.Customer;
import com.mycompany.interviewtask.entity.Status;
import com.mycompany.interviewtask.service.impl.CustomerServiceToDatabaseImpl;
import com.mycompany.interviewtask.utils.MyJsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceToDatabaseImplTest {

    @Autowired
    private CustomerServiceToDatabaseImpl customerService;


    @MockBean
    private MyJsonReader myJsonReader;

    @BeforeEach
    public void setUp() throws JsonMappingException, FileNotFoundException {
        Customer customer1 = new Customer(null, "Ivan", "Ivanov", Status.GOLD, 10, 1, "+1234567890", null);
        Customer customer2 = new Customer(null, "Nastya", "Ivanova", Status.SILVER, 5, 0, "+0987654321", null);

        List<Customer> mockCustomers = Arrays.asList(customer1, customer2);
        when(myJsonReader.getListCustomersFromJsonFile(PathConstants.PATH_OF_JSON_FILE)).thenReturn(mockCustomers);
    }

    @Test
    public void testWriteCustomers() throws JsonMappingException, FileNotFoundException {
        List<Customer> savedCustomers = customerService.writeCustomers();

        assertThat(savedCustomers).isNotNull();
        assertThat(savedCustomers.size()).isEqualTo(2);

        Customer customer1 = savedCustomers.get(0);
        assertThat(customer1.getFirstName()).isEqualTo("Ivan");
        assertThat(customer1.getRating()).isEqualTo(109); // 10 - 1 + 100

        Customer customer2 = savedCustomers.get(1);
        assertThat(customer2.getFirstName()).isEqualTo("Nastya");
        assertThat(customer2.getRating()).isEqualTo(15); // 5 - 0 + 10
    }}
