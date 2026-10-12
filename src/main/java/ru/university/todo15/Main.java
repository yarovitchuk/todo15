package ru.university.todo15;


import ru.university.todo15.controller.TodoCmdController;
import ru.university.todo15.model.Todo;
import ru.university.todo15.repository.TodoInMemoryRepository;
import ru.university.todo15.repository.TodoRepository;
import ru.university.todo15.service.TodoService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * 1. Написать todo list
 * 2. Создать, обновить, удалить, прочитать CRUD
 * 3. Должно быть в консоли
 * 4. Хранение должно быть в какой-то структуре данных Java
 */


public class Main {
    public static void main(String[] args) {
        Map<UUID, Todo> map = new HashMap<>();
        TodoRepository todoInMemoryRepository = new TodoInMemoryRepository(map);
        TodoService todoService = new TodoService(todoInMemoryRepository);
        Scanner scanner = new Scanner(System.in);
        TodoCmdController todoCmdController = new TodoCmdController(scanner, todoService);
        todoCmdController.run();
    }
}