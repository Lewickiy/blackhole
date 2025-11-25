package ru.levitsky.blackhole.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "block_luma", indexes = @Index(name = "idx_block_luma_hash", columnList = "hash"))
public class LumaBlock extends BlockBase {
    public LumaBlock() {
    }

    public LumaBlock(byte[] hash, byte[] data) {
        super(hash, data);
    }
}
