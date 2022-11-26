package com.spring.csvupload.api.service.impl;

import com.spring.csvupload.api.converter.ExerciseDtoConverter;
import com.spring.csvupload.api.exception.ApplicationException;
import com.spring.csvupload.api.model.dto.ExerciseDto;
import com.spring.csvupload.api.repository.ExerciseRepository;
import com.spring.csvupload.api.service.IExerciseService;
import com.spring.csvupload.api.utils.FileHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ExerciseService implements IExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Override
    public List<ExerciseDto> uploadCsvFile(MultipartFile file) {
        try {
            List<ExerciseDto> dtoList = FileHelper.csvToExerciseDto(file.getInputStream());
            exerciseRepository.saveAll(ExerciseDtoConverter.toEntityList(dtoList));
            return dtoList;
        } catch (IOException e)
        {
            throw ApplicationException.to("Fail to store csv data Error: %s",e.getMessage());
        }
    }

    @Override
    public List<ExerciseDto> getAll() {
      return   ExerciseDtoConverter.toDtoList(exerciseRepository.findAll()) ;
    }

    @Override
    public ExerciseDto getByCode(String code) {
        return ExerciseDtoConverter.fromEntity(exerciseRepository.getByCode(code));
    }

    @Override
    public void removeAll() {
        exerciseRepository.deleteAll();
    }
}
