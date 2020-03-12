package com.eteshis.repository;


import com.eteshis.domain.Todo;
import com.eteshis.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findByUser(User user);

    List<Todo> findByParentTodoId(Long parentTodoId);
}
