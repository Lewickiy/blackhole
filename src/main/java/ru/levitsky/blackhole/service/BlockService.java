package ru.levitsky.blackhole.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.levitsky.blackhole.mapper.BlockMapper;
import ru.levitsky.blackhole.dto.BlockResponse;
import ru.levitsky.blackhole.dto.BlockSaveRequest;
import ru.levitsky.blackhole.repository.BlockRepository;
import ru.levitsky.blackhole.entity.Block;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public BlockResponse saveBlock(BlockSaveRequest request) {
        Block entity = blockMapper.toEntity(request);
        Block savedBlock = blockRepository.save(entity);
        return blockMapper.toResponse(savedBlock);
    }

    public Optional<BlockResponse> getBlockByHash(String hash) {
        return blockRepository.findByHash(hash).map(blockMapper::toResponse);
    }
}
