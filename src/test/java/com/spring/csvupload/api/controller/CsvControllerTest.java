package com.spring.csvupload.api.controller;


import com.spring.csvupload.api.converter.ExerciseDtoConverter;
import com.spring.csvupload.api.service.IExerciseService;
import com.spring.csvupload.api.utils.TestDataHelper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.nio.file.Files;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CsvControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private IExerciseService exerciseService;
    private final String target = "/api/v1/files";


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CsvController csvController = new CsvController(exerciseService);
        mockMvc = MockMvcBuilders.standaloneSetup(csvController).build();
        when(exerciseService.getByCode(any())).thenReturn(ExerciseDtoConverter.fromEntity(TestDataHelper.getNewExercise())) ;
    }

    @Test
    void test_File_Upload() throws Exception {

        File file = new File("src/test/resources/static/exercise.csv");
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "exercise.csv", "text/csv",
                        Files.newInputStream(file.toPath()));
        mockMvc.perform(MockMvcRequestBuilders.multipart(target)
                .file(multipartFile))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items.*", hasSize(18)));
    }


    @Test
    void testDeleteAllData_thenReturnSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(target)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andDo(print()).andReturn();
    }

    @Test
    void testFindAllData_thenReturnSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(target)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items").isArray()).andReturn();
    }

    @Test
    void test_File_Upload_Empty_File() throws Exception {

        File file = new File("src/test/resources/static/exercise1.csv");
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "exercise1.csv", "text/csv",
                        Files.newInputStream(file.toPath()));
        mockMvc.perform(MockMvcRequestBuilders.multipart(target)
                .file(multipartFile))
                .andExpect(status().is2xxSuccessful()).andDo(print())
                .andReturn();
    }

}
