package com.spring.csvupload.api.service;

import com.spring.csvupload.api.model.dto.ExerciseDto;
import com.spring.csvupload.api.model.entity.Exercise;
import com.spring.csvupload.api.repository.ExerciseRepository;
import com.spring.csvupload.api.service.impl.ExerciseService;
import com.spring.csvupload.api.utils.TestDataHelper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExerciseServiceTest {
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private ExerciseRepository exerciseRepository;


    @Test
    public void testGetByIdAfter_Insertion() {
        Exercise initialObject = TestDataHelper.getNewExercise(21L);
        initialObject.setCode("New_Code");
        exerciseRepository.save(initialObject);
        Optional<Exercise> dataEntity = exerciseRepository.findById(21L);
        Assert.assertEquals(initialObject.getCode(), dataEntity.get().getCode());
    }

    @Test
    public void testGetByCode_After_Insertion() {
        Exercise initialObject = TestDataHelper.getNewExercise(7L);
        exerciseRepository.save(initialObject);
        Optional<Exercise> dataEntity = Optional.ofNullable(exerciseRepository.getByCode(initialObject.getCode()));
        assertThat(dataEntity.get().getCode()).isNotNull();
    }

    @Test
    public void testGetAll() {
        List<Exercise> dataEntities = exerciseRepository.findAll();
        assertThat(dataEntities).isNotNull();
    }

    @Test
    public void testGetAll_After_Insertion() {
        Exercise initialObject = TestDataHelper.getNewExercise(20L);
        initialObject.setCode("20L_Code");
        exerciseRepository.save(initialObject);
        List<Exercise> dataEntities = exerciseRepository.findAll();
        assertThat(dataEntities).isNotNull();
    }

    @Test
    public void test_File_Upload() throws IOException {
        File file = new File("src/test/resources/static/exercise.csv");
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "exercise.csv", "text/csv",
                        Files.newInputStream(file.toPath()));

        List<ExerciseDto> list = exerciseService.uploadCsvFile(multipartFile);
        assertThat(list).isNotNull().hasSize(18);
        List<Exercise> dataEntities = exerciseRepository.findAll();
        assertThat(dataEntities).isNotNull();
    }
}
