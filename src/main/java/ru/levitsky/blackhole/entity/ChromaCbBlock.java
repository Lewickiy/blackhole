package ru.levitsky.blackhole.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "block_chroma_cb", indexes = @Index(name = "idx_block_chroma_cb_hash", columnList = "hash"))
public class ChromaCbBlock extends BlockBase {
    public ChromaCbBlock() {
    }

    public ChromaCbBlock(byte[] hash, byte[] data) {
        super(hash, data);
    }
}
