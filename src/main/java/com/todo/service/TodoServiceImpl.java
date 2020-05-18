package com.todo.service;

import com.todo.constraints.MessageConstraints;
import com.todo.domain.Todo;
import com.todo.domain.User;
import com.todo.dto.TodoDTO;
import com.todo.exception.IllegalOperationException;
import com.todo.exception.NotFoundException;
import com.todo.repository.TodoRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public void addTodo(TodoDTO todoDTO, User user) {
        Todo todo = new Todo();
        todo.setUser(user);
        todo.setTitle(todoDTO.getTitle());
        todo.setDescription(todoDTO.getDescription());

        // TODO: burada aslinda parent todo benim todom mu kontrolu de yapilmali
        //  baskasinin todosunu kendime parent secemem lazim
        if (todoDTO.getParentTodoId() != null && todoDTO.getParentTodoId() != 0) {
            Optional<Todo> parent = todoRepository.findById(todoDTO.getParentTodoId());
            if (parent.isPresent()) {
                throw new NotFoundException(MessageConstraints.PARENT_TODO_NOT_FOUND);
            }
            todo.setParentTodoId(parent.get().getId());
        }

        todoRepository.save(todo);
    }

    @Override
    public List<TodoDTO> getTodos(String username) {
        User user = this.userService.findByUsername(username);
        List<Todo> todoList = this.todoRepository.findByUser(user);

        Type listType = new TypeToken<List<TodoDTO>>() {
        }.getType();

        return modelMapper.map(todoList, listType);
    }

    @Transactional
    @Override
    public void deleteTodo(Long todoId, String username) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);

        if (optionalTodo.isPresent()) {
            throw new NotFoundException(MessageConstraints.TODO_NOT_FOUND);
        }
        Todo todo = optionalTodo.get();
        // check if todo belong to login user
        if (todo.getUser().getUsername() != username) {
            throw new IllegalOperationException(MessageConstraints.BAD_CREDENTIALS);
        }


        // check if dependent todo exist
        List<Todo> todoList = todoRepository.findByParentTodoId(todo.getId());

        if (todoList != null && !todoList.isEmpty()) {
            throw new IllegalOperationException(MessageConstraints.DEPENDENT_TODO_EXIST, todoList.toString());
        }

        todoRepository.delete(todo);


    }
}
