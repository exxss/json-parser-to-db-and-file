package com.mycompany.interviewtask.service;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.FileNotFoundException;

public interface CustomerToFileService {
    void writeProneNumbersToFile(String path) throws JsonMappingException, FileNotFoundException;

}
