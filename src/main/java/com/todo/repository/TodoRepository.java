package com.todo.repository;


import com.todo.domain.Todo;
import com.todo.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findByUser(User user);

    List<Todo> findByParentTodoId(Long parentTodoId);
}
