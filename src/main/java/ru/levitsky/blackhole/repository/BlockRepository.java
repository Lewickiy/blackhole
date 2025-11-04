package ru.levitsky.blackhole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.levitsky.blackhole.singularity.entity.Block;

import java.util.UUID;

public interface BlockRepository extends JpaRepository<Block, UUID> {
}
