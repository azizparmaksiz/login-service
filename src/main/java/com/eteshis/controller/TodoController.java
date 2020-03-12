package com.eteshis.controller;

import com.eteshis.domain.User;
import com.eteshis.dto.TodoDTO;
import com.eteshis.service.TodoService;
import com.eteshis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;
    @Autowired
    private UserService userService;


    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTodoItem(@RequestBody TodoDTO todoDTO) {

      User user=  userService.getLoginUser();

        this.todoService.addTodo(todoDTO,user);
    }

    @GetMapping(value = "/all")
    public List<TodoDTO> getTodos() {

        User user=  userService.getLoginUser();
        return this.todoService.getTodos(user.getUsername());
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deletTodo(@PathVariable("id") Long id) {

        User user=  userService.getLoginUser();
         this.todoService.deleteTodo(id,user.getUsername());
    }

}
