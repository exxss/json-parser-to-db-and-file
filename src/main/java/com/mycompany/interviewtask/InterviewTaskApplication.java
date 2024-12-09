package com.mycompany.interviewtask;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mycompany.interviewtask.constants.PathConstants;
import com.mycompany.interviewtask.service.CustomerToDatabaseService;
import com.mycompany.interviewtask.service.impl.CustomerServiceToFileImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;

import java.io.FileNotFoundException;

@SpringBootApplication
public class InterviewTaskApplication {

    public static void main(String[] args) throws JsonMappingException, FileNotFoundException {
        ApplicationContext context = SpringApplication.run(InterviewTaskApplication.class, args);
        CustomerServiceToFileImpl customerServiceToFile = context.getBean(CustomerServiceToFileImpl.class);
        customerServiceToFile.writeProneNumbersToFile(PathConstants.PATH_OF_PHONE_NUMBERS);
    }

}
