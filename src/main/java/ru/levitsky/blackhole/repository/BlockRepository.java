package ru.levitsky.blackhole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.levitsky.blackhole.entity.Block;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlockRepository extends JpaRepository<Block, UUID> {
    List<Block> findAllByHashIn(List<String> hashes);
    Optional<Block> findByHash(String hash);
}
