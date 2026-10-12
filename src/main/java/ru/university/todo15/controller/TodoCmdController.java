package ru.university.todo15.controller;

import ru.university.todo15.model.Todo;
import ru.university.todo15.service.TodoService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TodoCmdController {

    private final Scanner scanner;
    private final TodoService todoService;

    public TodoCmdController(Scanner scanner, TodoService todoService) {
        this.scanner = scanner;
        this.todoService = todoService;
    }

    public void run() {
        while (true) {
            System.out.println("\n=== TO-DO МЕНЮ ===");
            System.out.println("1. Показать все задачи");
            System.out.println("2. Найти задачу по ID");
            System.out.println("3. Создать задачу");
            System.out.println("4. Обновить задачу");
            System.out.println("5. Удалить задачу");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String command = scanner.nextLine();

            try {
                switch (command) {
                    case "1":
                        showAll();
                        break;
                    case "2":
                        showById();
                        break;
                    case "3":
                        create();
                        break;
                    case "4":
                        update();
                        break;
                    case "5":
                        delete();
                        break;
                    case "0":
                        System.out.println("Выход из программы");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Неправильно введена команда");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showAll() {
        List<Todo> all = todoService.getAll();
        if (all.isEmpty()) {
            System.out.println("Заметок нет");
            return;
        }

        all.stream()
                .map(this::formatTodo)
                .forEach(System.out::println);
    }

    private void showById() {
        todoService.getById(getUUID())
                .map(this::formatTodo)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Заметки с заданным идентификатором не найдено")
                );
    }

    private void create() {
        System.out.print("Введите заголовок заметки: ");
        String title = scanner.nextLine();
        todoService.create(title);
        System.out.println("Успешно создано");
    }

    private void update() {
        todoService.getById(getUUID())
                .ifPresentOrElse(
                        todo -> {
                            String newTitle = scanner.nextLine();
                            Todo updatedTodo = new Todo(todo.id(), newTitle);
                            Todo savedTodo = todoService.update(updatedTodo);
                            System.out.println("Заметка обновлена: " + formatTodo(savedTodo));
                        },
                        () -> System.out.println("Заметки с заданным идентификатором не найдено")
                );
    }

    private void delete() {
        UUID uuid = getUUID();
        if (todoService.deleteById(uuid)) {
            System.out.println("Успешно удалено с идентификатором = " + uuid);
        }
    }

    private UUID getUUID() {
        while (true) {
            System.out.print("Введите идентификатор заметки: ");
            String id = scanner.nextLine();
            try {
                return UUID.fromString(id);
            } catch (Exception e) {
                System.out.println("Неправильно указан идентификатор");
            }
        }
    }


    private String formatTodo(Todo todo) {
        return "id = " + todo.id() + " title = " + todo.title();
    }
}
