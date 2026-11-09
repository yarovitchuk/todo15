package ru.university.todo15.repository;

import ru.university.todo15.model.Todo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TodoDatabaseRepository implements TodoRepository {


    private final DataSource dataSource;

    public TodoDatabaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Todo create(Todo todo) {
        var sql = """
                    INSERT INTO todo(id, title)
                    VALUES (?::UUID, ?)
                    """;

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, todo.id().toString());
            statement.setString(2, todo.title());
            statement.executeUpdate();
            return todo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Todo update(Todo todo) {
        var sql = """
                    UPDATE todo
                    SET title = ?
                    WHERE id = ?::UUID
                    """;

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, todo.title());
            statement.setString(2, todo.id().toString());
            statement.executeUpdate();
            return todo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(UUID id) {
        var sql = """
                    DELETE FROM todo
                    WHERE id = ?::UUID
                    """;

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id.toString());
            int deletedRows = statement.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException _) {
            return false;
        }
    }

    @Override
    public List<Todo> list() {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            var sql = """
                    SELECT t.id, t.title
                    FROM todo t
                    """;

            ResultSet rs = statement.executeQuery(sql);
            List<Todo> todos = new ArrayList<>();

            while (rs.next()) {
                var todo = mapRowToTodo(rs);
                todos.add(todo);
            }

            return todos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Todo> getById(UUID id) {
        var sql = """
                SELECT t.id, t.title
                FROM todo t
                WHERE t.id = ?::UUID
                """;
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToTodo(rs));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Todo mapRowToTodo(ResultSet rs) throws SQLException {
        return new Todo(rs.getObject("id", UUID.class),
                rs.getString("title"));
    }
}
