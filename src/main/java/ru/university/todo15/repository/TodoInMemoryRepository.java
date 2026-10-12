package ru.university.todo15.repository;

import ru.university.todo15.model.Todo;

import java.util.*;

public class TodoInMemoryRepository implements TodoRepository {

    private final Map<UUID, Todo> data;

    public TodoInMemoryRepository(Map<UUID, Todo> data) {
        this.data = data;
    }

    public TodoInMemoryRepository() {
        this.data = new HashMap<>();
    }

    public Todo create(Todo todo) {
        var id = todo.id();
        data.put(id, todo);
        return todo;
    }

    public Todo update(Todo todo) {
        var id = todo.id();
        data.put(id, todo);
        return todo;
    }

    public Optional<Todo> getById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }

    public List<Todo> list() {
        return data.values().stream().toList();
    }
}
