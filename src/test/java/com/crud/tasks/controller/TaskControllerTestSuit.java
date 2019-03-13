package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuit {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService service;

    @Test
    public void testGetTasks() throws Exception {
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto(1L, "Blue task", "Blue content"));
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Blue task", "Blue content"));

        when(service.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is( 1)))
                .andExpect(jsonPath("$[0].title", is("Blue task")))
                .andExpect(jsonPath("$[0].content", is("Blue content")));
    }
    @Test
    public void testGetTask() throws Exception {
        //Given
        Task task = new Task(3L, "Blue task", "Blue content");
        when(service.getTaskById(3L)).thenReturn(Optional.ofNullable(task));
        TaskDto taskDto = new TaskDto(3L, "Blue task", "Blue content");
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("Blue task")))
                .andExpect(jsonPath("$.content", is("Blue content")));
    }
    @Test
    public void testDeleteTask() throws Exception {
        //Then
        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service,times(1)).deleteTaskById(2L);
    }
    @Test
    public void testUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(3L, "Red task", "Red content");
        Task task = new Task(3L, "Red task", "Red content");

        //when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTask(any())).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        System.out.println(jsonContent);

       // When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("Red task")))
                .andExpect(jsonPath("$.content", is("Red content")));
    }
    @Test
    public void testCreateTask () throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(3L, "Red task", "Red content");
        Task task = new Task(3L, "Red task", "Red content");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
       verify(taskMapper,times(1)).mapToTask(taskDto);
       verify(service,times(1)).saveTask(task);

    }
}
