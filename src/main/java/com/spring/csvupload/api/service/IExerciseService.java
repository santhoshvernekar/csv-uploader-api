package com.spring.csvupload.api.service;

import com.spring.csvupload.api.model.dto.ExerciseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IExerciseService {
    List<ExerciseDto> uploadCsvFile(MultipartFile file);

    List<ExerciseDto> getAll();

    ExerciseDto getByCode(String code);

    void removeAll();
}
