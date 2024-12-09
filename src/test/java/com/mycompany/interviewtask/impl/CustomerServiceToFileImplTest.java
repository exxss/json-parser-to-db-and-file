package com.mycompany.interviewtask.impl;

import com.mycompany.interviewtask.entity.Customer;
import com.mycompany.interviewtask.entity.Status;
import com.mycompany.interviewtask.service.impl.CustomerServiceToFileImpl;
import com.mycompany.interviewtask.utils.MyJsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceToFileImplTest {

    @Autowired
    private CustomerServiceToFileImpl customerServiceToFileImpl;

    @MockBean
    private MyJsonReader myJsonReader;

    private final String TEST_PHONE_NUMBERS_PATH = "test_phone_numbers";

    @BeforeEach
    void setUp() throws IOException {
        Files.createFile(Path.of(TEST_PHONE_NUMBERS_PATH));
    }
    @AfterEach
    void cleanUp() throws IOException {
        Files.delete(Path.of(TEST_PHONE_NUMBERS_PATH));
    }

    @Test
    void testWriteProneNumbersToFile_withValidData() throws Exception {
        Customer customer1 = new Customer(null, "Ivan", "Ivanov", Status.GOLD, 10, 1, "+1234567890", null);
        Customer customer2 = new Customer(null, "Nastya", "Ivanova", Status.SILVER, 5, 0, "+0987654321", null);
        List<Customer> mockCustomers = Arrays.asList(customer1, customer2);

        when(myJsonReader.getListCustomersFromJsonFile(anyString())).thenReturn(mockCustomers);

        customerServiceToFileImpl.writeProneNumbersToFile(TEST_PHONE_NUMBERS_PATH);

        List<String> writtenLines = Files.readAllLines(Path.of(TEST_PHONE_NUMBERS_PATH));
        assertEquals(2, writtenLines.size());
        assertEquals("+0987654321", writtenLines.get(0));
        assertEquals("+1234567890", writtenLines.get(1));
    }
}



