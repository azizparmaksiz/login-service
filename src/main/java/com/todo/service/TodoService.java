package com.todo.service;

import com.todo.domain.User;
import com.todo.dto.TodoDTO;

import java.util.List;

/**
 * Created by Dell on 12.03.2020.
 */
public interface TodoService {

    void addTodo(TodoDTO todoDTO, User user);

    List<TodoDTO> getTodos(String username);

    void deleteTodo(Long id, String username);
}
