package ru.university.todo15.repository;

import ru.university.todo15.model.Todo;

import java.util.*;

public class TodoRepo {

    private final Map<UUID, Todo> data;

    public TodoRepo(Map<UUID, Todo> data) {
        this.data = data;
    }

    public TodoRepo() {
        this.data = new HashMap<>();
    }

    public void create(Todo todo) {
        var id = todo.id();
        data.put(id, todo);
    }

    public void update(Todo todo) {
        var id = todo.id();
        data.put(id, todo);
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
