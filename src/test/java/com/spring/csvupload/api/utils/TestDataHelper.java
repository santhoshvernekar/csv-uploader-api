package com.spring.csvupload.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.csvupload.api.model.entity.Exercise;

public class TestDataHelper {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static Exercise getNewExercise() {
       return Exercise.builder().code("Test_Code")
                .codeListCode("Code")
                .sortingPriority(1)
                .build();
    }
}
