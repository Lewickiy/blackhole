package ru.levitsky.blackhole.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levitsky.blackhole.dto.BlockResponse;
import ru.levitsky.blackhole.dto.BlockSaveRequest;
import ru.levitsky.blackhole.entity.Block;
import ru.levitsky.blackhole.mapper.BlockMapper;
import ru.levitsky.blackhole.repository.BlockRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

        return blockMapper.toResponse(savedBlock);
    }

    @Transactional(readOnly = true)
    public Optional<BlockResponse> getBlockByHash(String hash) {
        Optional<BlockResponse> response = blockRepository.findByHash(hash).map(blockMapper::toResponse);
        log.info("returns block: {}", response);

        return response;
    }

    /**
     * Returns a list of hashes that are not present in the database<br>
     * This method is used for batch deduplication — to determine which blocks
     * need to be uploaded by comparing the provided list of hashes with the
     * ones already stored in the system<br>
     *
     * @param hashes list of hashes to check for existence
     * @return list of hashes that are missing in the database
     */
    @Transactional(readOnly = true)
    public List<String> findMissingHashes(List<String> hashes) {
        Set<String> existing = blockRepository.findAllByHashIn(hashes)
                .stream()
                .map(Block::getHash)
                .collect(Collectors.toSet());

        return hashes.stream()
                .filter(hash -> !existing.contains(hash))
                .toList();
    }

    /**
     * Saves a batch of new blocks to the database and returns their representations<br>
     * Each block in the request is mapped to an entity, persisted using {@link org.springframework.data.jpa.repository.JpaRepository#saveAll(Iterable)},
     * and then converted back to a DTO response.<br>
     * The method is typically used to store missing blocks detected via {@link #findMissingHashes(List)}<br>
     *
     * @param blockSaveRequestList list of blocks to save
     * @return list of saved blocks represented as {@link BlockResponse}
     */
    @Transactional
    public List<BlockResponse> saveBlocksBatch(List<BlockSaveRequest> blockSaveRequestList) {
        var entities = blockSaveRequestList.stream()
                .map(blockMapper::toEntity)
                .toList();

        var saved = blockRepository.saveAll(entities);

        return saved.stream()
                .map(blockMapper::toResponse)
                .toList();
    }
}
