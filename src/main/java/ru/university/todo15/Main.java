package ru.university.todo15;


import org.postgresql.ds.PGSimpleDataSource;
import ru.university.todo15.controller.TodoCmdController;
import ru.university.todo15.repository.TodoDatabaseRepository;
import ru.university.todo15.repository.TodoRepository;
import ru.university.todo15.service.TodoService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 1. Написать todo list
 * 2. Создать, обновить, удалить, прочитать CRUD
 * 3. Должно быть в консоли
 * 4. Хранение должно быть в какой-то структуре данных Java
 */


public class Main {
    public static void main(String[] args) {
        DataSource dataSource = connectDB();
        initDB(dataSource);
        TodoRepository todoInMemoryRepository = new TodoDatabaseRepository(dataSource);
        TodoService todoService = new TodoService(todoInMemoryRepository);
        Scanner scanner = new Scanner(System.in);
        TodoCmdController todoCmdController = new TodoCmdController(scanner, todoService);
        todoCmdController.run();
    }

    public static DataSource connectDB() {
        var datasource = new PGSimpleDataSource();

        datasource.setServerNames(new String[]{"localhost"});
        datasource.setPortNumbers(new int[]{5432});
        datasource.setDatabaseName("todo");
        datasource.setUser("todo");
        datasource.setPassword("todo");

        return datasource;
    }

    public static void initDB(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            var sql = """
                    CREATE TABLE IF NOT EXISTS todo(
                        id UUID PRIMARY KEY,
                        title TEXT NOT NULL
                    )
                    """;
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}