package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTestSuite {

    @InjectMocks
    private DbService dbService;
    @Mock
    private TaskRepository repository;

    @Test
    public void testGetAllTasks() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "BlueTask", "VeryBlue"));
        //When
        when(repository.findAll()).thenReturn(taskList);
        //Then
        assertNotNull(taskList);
        assertEquals(1, taskList.size());

        taskList.forEach(task -> {
            assertEquals((Object) 1L, (Object) task.getId());
            assertEquals("BlueTask", task.getTitle());
            assertEquals("VeryBlue", task.getContent());
        });
    }
    @Test
    public void testSaveTask() {
        //Given
        Task task = new Task(1L, "BlueTask", "VeryBlue");
        //When
        when(repository.save(task)).thenReturn(task);
        //Then
        assertEquals((Object) 1L, (Object) task.getId());
        assertEquals("BlueTask", task.getTitle());
        assertEquals("VeryBlue", task.getContent());
    }
    @Test
    public void testGetTaskById() {
        //Given
        Task task = new Task(1L,"BlueTask", "VeryBlue");
        //When
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(task));
        //Then
        assertEquals("BlueTask", task.getTitle());
        assertEquals("VeryBlue", task.getContent());
    }
}




