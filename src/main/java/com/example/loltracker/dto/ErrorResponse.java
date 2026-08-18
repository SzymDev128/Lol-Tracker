package com.example.loltracker.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse (
    int status,
    String error,
    List<String> messages,
    LocalDateTime timestamp
) {}