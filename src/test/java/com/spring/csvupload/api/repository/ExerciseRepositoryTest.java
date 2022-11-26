package com.spring.csvupload.api.repository;

import com.spring.csvupload.api.model.entity.Exercise;
import com.spring.csvupload.api.utils.TestDataHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ExerciseRepositoryTest {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    public void test_Find_All() {
        List<Exercise> exerciseList = exerciseRepository.findAll();
        Assert.assertEquals(exerciseList.size(), 0);
    }

    @Test
    public void test_Find_All_After_Insertion() {
        List<Exercise> previousExerciseList = exerciseRepository.findAll();
        exerciseRepository.save(TestDataHelper.getNewExercise(2L));
        List<Exercise> newExerciseList = exerciseRepository.findAll();
        Assert.assertEquals(newExerciseList.size(), previousExerciseList.size() + 1);
    }

    @Test
    public void test_Delete_All() {
        exerciseRepository.save(TestDataHelper.getNewExercise(3L));
        List<Exercise> previousExerciseList = exerciseRepository.findAll();
        exerciseRepository.deleteAll();
        List<Exercise> newExerciseList = exerciseRepository.findAll();
        Assert.assertEquals(newExerciseList.size(), previousExerciseList.size() - 1);

    }

    @Test
    public void test_Find_One_By_Id() {
        Exercise mockExercise = TestDataHelper.getNewExercise(4L);
        exerciseRepository.save(mockExercise);
        Optional<Exercise> exercise = exerciseRepository.findById(4L);
        Assert.assertEquals(exercise.get().getCode(), mockExercise.getCode());
    }

    @Test
    public void test_Find_One_By_Code() {
        Exercise mockExercise = TestDataHelper.getNewExercise(5L);
        exerciseRepository.save(mockExercise);
        Exercise exercise = exerciseRepository.getByCode(mockExercise.getCode());
        Assert.assertEquals(exercise.getCode(), mockExercise.getCode());
    }
}
