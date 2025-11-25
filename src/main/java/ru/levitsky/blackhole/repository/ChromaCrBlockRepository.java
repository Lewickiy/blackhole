package ru.levitsky.blackhole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.levitsky.blackhole.entity.ChromaCrBlock;

import java.util.List;
import java.util.Optional;

public interface ChromaCrBlockRepository extends JpaRepository<ChromaCrBlock, byte[]> {
    List<ChromaCrBlock> findAllByHashIn(List<byte[]> hashes);

    Optional<ChromaCrBlock> findByHash(byte[] hash);
}