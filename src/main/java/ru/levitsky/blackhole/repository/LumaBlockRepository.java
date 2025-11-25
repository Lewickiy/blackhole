package ru.levitsky.blackhole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.levitsky.blackhole.entity.LumaBlock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LumaBlockRepository extends JpaRepository<LumaBlock, UUID> {

    List<LumaBlock> findAllByHashIn(List<byte[]> hashes);

    Optional<LumaBlock> findByHash(byte[] hash);
}
