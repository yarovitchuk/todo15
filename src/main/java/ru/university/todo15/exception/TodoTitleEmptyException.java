package ru.university.todo15.exception;

public class TodoTitleEmptyException extends BusinessException {
    public TodoTitleEmptyException() {
        super("Пустой заголовок");
    }
}
