package ru.levitsky.blackhole.eventhorizon.dto;

import java.time.Instant;

public record BlockRequest(
        String hash,
        Instant createdAt,
        byte[] data,
        int size) {}
