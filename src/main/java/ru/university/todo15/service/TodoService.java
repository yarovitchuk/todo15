package ru.university.todo15.service;

import ru.university.todo15.exception.TodoNotFoundException;
import ru.university.todo15.exception.TodoTitleEmptyException;
import ru.university.todo15.model.Todo;
import ru.university.todo15.repository.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public Todo create(String title) {
        if (title.isBlank()) {
            throw new TodoTitleEmptyException();
        }

        var id = UUID.randomUUID();
        var todo = new Todo(id, title);
        repo.create(todo);
        return todo;
    }

    public Todo update(Todo newTodo) {
        if (newTodo.title().isBlank()) {
            throw new TodoTitleEmptyException();
        }

        repo.update(newTodo);
        return newTodo;
    }

    public List<Todo> getAll() {
        return repo.list();
    }

    public Optional<Todo> getById(UUID id) {
        return repo.getById(id);
    }

    public boolean deleteById(UUID id) {
        if (!repo.delete(id)) {
            throw new TodoNotFoundException(id);
        }

        return true;
    }
}
