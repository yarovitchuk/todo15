package ru.university.todo15.exception;

import java.util.UUID;

public class TodoNotFoundException extends BusinessException {

    public TodoNotFoundException(UUID id) {
        super("Заметка не найдена: id = " + id);
    }
}
