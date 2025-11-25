package ru.levitsky.blackhole.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BlockBase {

    @Id
    @Column(nullable = false, unique = true,  columnDefinition = "BYTEA")
    private byte[] hash;

    @Column(nullable = false, updatable = false, insertable = false)
    private Instant createdAt;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private byte[] data;

    public BlockBase(byte[] hash, byte[] data) {
        this.hash = hash;
        this.data = data;
    }
}
