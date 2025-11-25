package ru.levitsky.blackhole.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.levitsky.blackhole.enumeration.BlockType;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockResponse {
    private String hash;
    private byte[] data;
    private Instant createdAt;
    private BlockType type;
}
