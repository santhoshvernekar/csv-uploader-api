package com.spring.csvupload.api.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExerciseListDto {
    private List<ExerciseDto> items;
}
