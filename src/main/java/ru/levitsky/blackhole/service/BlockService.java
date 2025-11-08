package ru.levitsky.blackhole.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levitsky.blackhole.mapper.BlockMapper;
import ru.levitsky.blackhole.dto.BlockResponse;
import ru.levitsky.blackhole.dto.BlockSaveRequest;
import ru.levitsky.blackhole.repository.BlockRepository;
import ru.levitsky.blackhole.entity.Block;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockService {

    @PersistenceContext
    private EntityManager entityManager;
    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public BlockResponse saveBlock(BlockSaveRequest request) {
        Block entity = blockMapper.toEntity(request);
        Block savedBlock = blockRepository.saveAndFlush(entity);
        entityManager.refresh(savedBlock);
        return blockMapper.toResponse(savedBlock);
    }

    @Transactional(readOnly = true)
    public Optional<BlockResponse> getBlockByHash(String hash) {
        Optional<BlockResponse> response = blockRepository.findByHash(hash).map(blockMapper::toResponse);
        log.info("returns block: {}", response);
        return response;
    }
}
