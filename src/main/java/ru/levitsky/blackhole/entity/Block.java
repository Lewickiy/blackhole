package ru.levitsky.blackhole.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "block", indexes = {
        @Index(name = "idx_block_hash", columnList = "hash")
})
public class Block {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "hash", nullable = false, unique = true, length = 64)
    private String hash;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Instant createdAt;

    @Setter(AccessLevel.NONE)
    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;

    @Setter(AccessLevel.NONE)
    @Column(name = "size", nullable = false)
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
