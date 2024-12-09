package com.mycompany.interviewtask.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.interviewtask.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class MyJsonReader {

    private final ObjectMapper objectMapper;

    public List<Customer> getListCustomersFromJsonFile(String path) throws JsonMappingException, FileNotFoundException {
        File file = validateFile(path);
        try {
            return objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            log.error("Failed to parse JSON file at path: {}", path, e);
            throw new JsonMappingException("Error reading or parsing JSON file: " + path, e);
        }
    }

    private File validateFile(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            log.error("JSON file does not exist: {}", path);
            throw new FileNotFoundException("JSON file not found: " + path);
        }
        if (file.length() == 0) {
            log.error("JSON file is empty: {}", path);
            throw new IllegalArgumentException("JSON file is empty: " + path);
        }
        return file;
    }
}

