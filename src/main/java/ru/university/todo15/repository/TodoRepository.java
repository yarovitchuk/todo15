package ru.university.todo15.repository;

import ru.university.todo15.model.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepository {

    Todo create(Todo todo);

    Todo update(Todo todo);

    boolean delete(UUID id);

    List<Todo> list();

    Optional<Todo> getById(UUID id);
}
