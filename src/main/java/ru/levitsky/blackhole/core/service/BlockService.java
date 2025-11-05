package ru.levitsky.blackhole.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.levitsky.blackhole.core.mapper.BlockMapper;
import ru.levitsky.blackhole.eventhorizon.dto.BlockRequest;
import ru.levitsky.blackhole.eventhorizon.dto.BlockResponse;
import ru.levitsky.blackhole.repository.BlockRepository;
import ru.levitsky.blackhole.singularity.entity.Block;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public BlockResponse saveBlock(BlockRequest request) {
        Block savedBlock = blockRepository.save(blockMapper.requestToEntity(request));
        return blockMapper.entityToResponse(savedBlock);
    }

    public Optional<Block> getBlockByHash(String hash) {
        return blockRepository.findByHash(hash);
    }
}
