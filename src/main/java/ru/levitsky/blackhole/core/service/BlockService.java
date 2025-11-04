package ru.levitsky.blackhole.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.levitsky.blackhole.repository.BlockRepository;
import ru.levitsky.blackhole.singularity.entity.Block;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;

    public Block saveBlock(Block block) {
        if (block.getId() == null) {
            block.setId(UUID.randomUUID());
        }
        return blockRepository.save(block);
    }

    public Optional<Block> getBlockByHash(String hash) {
        return blockRepository.findByHash(hash);
    }
}
