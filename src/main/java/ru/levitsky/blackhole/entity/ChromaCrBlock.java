package ru.levitsky.blackhole.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "block_chroma_cr", indexes = @Index(name = "idx_block_chroma_cr_hash", columnList = "hash"))
public class ChromaCrBlock extends BlockBase {
    public ChromaCrBlock() {
    }

    public ChromaCrBlock(byte[] hash, byte[] data) {
        super(hash, data);
    }
}
