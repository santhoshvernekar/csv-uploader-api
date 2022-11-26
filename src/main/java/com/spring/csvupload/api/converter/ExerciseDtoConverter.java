package com.spring.csvupload.api.converter;

import com.spring.csvupload.api.model.dto.ExerciseDto;
import com.spring.csvupload.api.model.entity.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDtoConverter {

    public static ExerciseDto fromEntity(Exercise entity) {
        return ExerciseDto.builder().code(entity.getCode())
                .source(entity.getSource()).
                        codeListCode(entity.getCodeListCode())
                .displayValue(entity.getDisplayValue())
                .longDescription(entity.getLongDescription())
                .fromDate(entity.getFromDate())
                .toDate(entity.getToDate()).sortingPriority(entity.getSortingPriority())
                .build();
    }

    public static Exercise toEntity(ExerciseDto dto) {
        return Exercise.builder()
                .code(dto.getCode())
                .source(dto.getSource()).
                        codeListCode(dto.getCodeListCode())
                .displayValue(dto.getDisplayValue())
                .longDescription(dto.getLongDescription())
                .fromDate(dto.getFromDate())
                .toDate(dto.getToDate()).sortingPriority(dto.getSortingPriority()).
                        build();
    }

    public static List<Exercise> toEntityList(List<ExerciseDto> dtoList) {
        List<Exercise> entityList = new ArrayList<>();
        for (ExerciseDto e : dtoList) {
            entityList.add(toEntity(e));
        }
        return entityList;
    }

    public static List<ExerciseDto> toDtoList(List<Exercise> entityList) {
        List<ExerciseDto> dtoList = new ArrayList<>();
        for (Exercise e : entityList) {
            dtoList.add(fromEntity(e));
        }
        return dtoList;
    }


}
