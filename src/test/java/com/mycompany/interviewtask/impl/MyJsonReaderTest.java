package com.mycompany.interviewtask.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.interviewtask.entity.Customer;
import com.mycompany.interviewtask.entity.Status;
import com.mycompany.interviewtask.utils.MyJsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyJsonReaderTest {

    @MockBean
    private MyJsonReader myJsonReader;

    @MockBean
    private ObjectMapper objectMapper;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        objectMapper = mock(ObjectMapper.class);
        myJsonReader = new MyJsonReader(objectMapper);
    }

    @Test
    void testGetListCustomersFromJsonFileValidFile() throws Exception {
        File jsonFile = tempDir.resolve("customers.json").toFile();
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("[{\"id\":1,\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\"}]");
        }

        List<Customer> expectedCustomers = List.of(new Customer(1L, "Ivan", "Ivanov", Status.GOLD, 5, 0, "+123456789", 5));
        when(objectMapper.readValue(eq(jsonFile), any(TypeReference.class))).thenReturn(expectedCustomers);

        List<Customer> actualCustomers = myJsonReader.getListCustomersFromJsonFile(jsonFile.getAbsolutePath());

        assertEquals(expectedCustomers, actualCustomers, "Customers list should match expected values");
    }

    @Test
    void testGetListCustomersFromJsonFileFileNotFound() {
        String invalidPath = "noexist.json";

        FileNotFoundException exception = assertThrows(FileNotFoundException.class, () ->
                myJsonReader.getListCustomersFromJsonFile(invalidPath));
        assertEquals("JSON file not found: " + invalidPath, exception.getMessage());
    }

    @Test
    void testGetListCustomersFromJsonFileEmptyFile() throws Exception {
        File emptyFile = tempDir.resolve("empty.json").toFile();
        assertTrue(emptyFile.createNewFile(), "Empty file should be created");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                myJsonReader.getListCustomersFromJsonFile(emptyFile.getAbsolutePath()));
        assertEquals("JSON file is empty: " + emptyFile.getAbsolutePath(), exception.getMessage());
    }
}

