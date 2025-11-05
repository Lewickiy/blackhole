package ru.levitsky.blackhole.eventhorizon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockRequest {
    private String hash;
    private Instant createdAt;
    private byte[] data;
    private int size;
}
