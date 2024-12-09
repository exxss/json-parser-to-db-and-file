package com.mycompany.interviewtask.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mycompany.interviewtask.constants.PathConstants;
import com.mycompany.interviewtask.entity.Customer;
import com.mycompany.interviewtask.service.CustomerToFileService;
import com.mycompany.interviewtask.utils.MyJsonReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerServiceToFileImpl implements CustomerToFileService {

    private final MyJsonReader myJsonReader;

    @Override
    public void writeProneNumbersToFile(String path) throws JsonMappingException, FileNotFoundException {
        List<Customer> customers = myJsonReader.getListCustomersFromJsonFile(PathConstants.PATH_OF_JSON_FILE);
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path))) {
            sortPhoneNumbers(customers, number -> !number.startsWith("+7"), true, br);
            sortPhoneNumbers(customers, number -> number.startsWith("+7"), false, br);
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void sortPhoneNumbers(List<Customer> customers, Predicate<String> filter, boolean sort, BufferedWriter br) {
        customers.stream()
                .map(Customer::getPhoneNumber)
                .filter(filter)
                .sorted(sort ? Comparator.naturalOrder() : Comparator.comparingInt(a -> 0))
                .forEach(number -> writeNumberToFile(br, number));
    }

    private void writeNumberToFile(BufferedWriter br, String number) {
        try {
            br.write(number + "\n");
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new UncheckedIOException(e);
        }
    }
}
