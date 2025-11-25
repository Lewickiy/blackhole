package ru.levitsky.blackhole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.levitsky.blackhole.entity.ChromaCbBlock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChromaCbBlockRepository extends JpaRepository<ChromaCbBlock, UUID> {
    List<ChromaCbBlock> findAllByHashIn(List<byte[]> hashes);

    Optional<ChromaCbBlock> findByHash(byte[] hash);
}
