package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskDto)) return false;
        TaskDto taskDto = (TaskDto) o;
        return getId().equals(taskDto.getId()) &&
                getTitle().equals(taskDto.getTitle()) &&
                getContent().equals(taskDto.getContent());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
