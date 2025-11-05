package ru.levitsky.blackhole.eventhorizon.dto;

import java.time.Instant;
import java.util.UUID;

public record BlockResponse(
        UUID id,
        String hash,
        Instant createdAt,
        byte[] data,
        int size) {}
