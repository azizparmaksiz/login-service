package com.todo.dto;


public class TodoDTO {
    private Long id;
    private String title;
    private String description;
    private Long parentTodoId;

    public Long getParentTodoId() {
        return parentTodoId;
    }

    public void setParentTodoId(Long parentTodoId) {
        this.parentTodoId = parentTodoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
