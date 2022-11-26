package com.spring.csvupload.api.controller;

import com.spring.csvupload.api.exception.ApplicationException;
import com.spring.csvupload.api.model.dto.ExerciseDto;
import com.spring.csvupload.api.model.dto.ExerciseListDto;
import com.spring.csvupload.api.model.dto.StatusResponse;
import com.spring.csvupload.api.service.IExerciseService;
import com.spring.csvupload.api.utils.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/files")
@RestController
public class CsvController {
    private final IExerciseService exerciseService;

    @Autowired
    public CsvController(IExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }


    @PostMapping()
    public ResponseEntity<ExerciseListDto> uploadFile(@RequestParam("file") MultipartFile file) {
        ExerciseListDto list = new ExerciseListDto();
        List<ExerciseDto> csvEntityDtos = null;
        if (FileHelper.hasCSVFormat(file)) {
            if (file.isEmpty()) {
                throw new ApplicationException("File is empty.");  // Bad Request from Exception handler
            }
            csvEntityDtos = exerciseService.uploadCsvFile(file);
            list.setItems(csvEntityDtos);
            if (csvEntityDtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(list);
        } else {
            throw new ApplicationException("File is Not in CSV Format.");
        }
    }

    @GetMapping
    public ResponseEntity<ExerciseListDto> getAll() {
        ExerciseListDto list = new ExerciseListDto();
        list.setItems(exerciseService.getAll());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ExerciseDto> getByCode(@PathVariable("code") String code) {
        if (code == null || "".equals(code)) {
            throw new ApplicationException("Invalid Code name.");
        }
        ExerciseDto item = exerciseService.getByCode(code);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping
    public ResponseEntity<StatusResponse> removeAll() {
        exerciseService.removeAll();
        return new ResponseEntity<>(new StatusResponse("Removed Successfully"), HttpStatus.OK);
    }
}
