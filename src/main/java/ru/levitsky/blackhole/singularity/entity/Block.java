package ru.levitsky.blackhole.singularity.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Block {

    private UUID id;
    private String hash;
    private Instant createdAt = Instant.now();
    @Setter(AccessLevel.NONE)
    private byte[] data;
    @Setter(AccessLevel.NONE)
    private int size;

    public Block(String hash, byte[] data) {
        this.hash = hash;
        this.data = data;
        this.size = data.length;
    }

    public void setData(byte[] data) {
        this.data = data;
        this.size = data.length;
    }
}
