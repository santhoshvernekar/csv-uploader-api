package com.spring.csvupload.api.utils;

import com.spring.csvupload.api.exception.ApplicationException;
import com.spring.csvupload.api.model.dto.ExerciseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileHelper {
    public static final String TYPE = "text/csv";
    public static int lengthConstraint = 2000;

    public static boolean hasCSVFormat(MultipartFile file) {
        return !file.isEmpty() && TYPE.equals(file.getContentType());
    }

    public static List<ExerciseDto> csvToExerciseDto(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<ExerciseDto> ExerciseDtoList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                ExerciseDto exerciseDto = getExerciseDto(csvRecord);
                ExerciseDtoList.add(exerciseDto);
            }
            return ExerciseDtoList;
        } catch (IOException e) {
            throw ApplicationException.to("Fail to parse CSV file: %s", e.getMessage());
        }
    }


    private static ExerciseDto getExerciseDto(CSVRecord csvRecord) {
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setSource(csvRecord.get("source"));
        exerciseDto.setCode(csvRecord.get("code"));
        exerciseDto.setCodeListCode(csvRecord.get("codeListCode"));
        exerciseDto.setDisplayValue(getValidStringValue(csvRecord.get("displayValue"), "displayValue"));
        exerciseDto.setLongDescription(getValidStringValue(csvRecord.get("longDescription"), "longDescription"));
        exerciseDto.setFromDate(DateConverter.convertDate(csvRecord.get("fromDate"), DateConverter.DATE_FORMAT_MONTH_DAY_YEAR_WITH_DASHES));
        exerciseDto.setToDate(DateConverter.convertDate(csvRecord.get("toDate"), DateConverter.DATE_FORMAT_MONTH_DAY_YEAR_WITH_DASHES));
        if (csvRecord.get("sortingPriority").isEmpty()) {
            exerciseDto.setSortingPriority(0);
        } else {
            exerciseDto.setSortingPriority(Integer.valueOf(csvRecord.get("sortingPriority")));
        }
        return exerciseDto;
    }

    public static String getValidStringValue(String stringField, String fieldName) {
        return StringValidator.getValidStringValue(stringField,fieldName);
    }
}
