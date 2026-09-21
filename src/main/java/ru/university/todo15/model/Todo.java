package ru.university.todo15.model;

import java.util.UUID;

public record Todo(
   UUID id,
   String title
) {}
