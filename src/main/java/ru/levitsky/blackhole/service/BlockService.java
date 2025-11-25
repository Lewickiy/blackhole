package ru.levitsky.blackhole.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levitsky.blackhole.dto.BlockResponse;
import ru.levitsky.blackhole.dto.BlockSaveRequest;
import ru.levitsky.blackhole.entity.BlockBase;
import ru.levitsky.blackhole.entity.ChromaCbBlock;
import ru.levitsky.blackhole.entity.ChromaCrBlock;
import ru.levitsky.blackhole.entity.LumaBlock;
import ru.levitsky.blackhole.enumeration.BlockType;
import ru.levitsky.blackhole.mapper.BlockMapper;
import ru.levitsky.blackhole.repository.BlockRepositoryCustom;
import ru.levitsky.blackhole.repository.ChromaCbBlockRepository;
import ru.levitsky.blackhole.repository.ChromaCrBlockRepository;
import ru.levitsky.blackhole.repository.LumaBlockRepository;
import ru.levitsky.blackhole.util.HashUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockService {

    private static final int BATCH_SIZE = 10000;

    private final BlockRepositoryCustom lumaRepoCustom;
    private final BlockRepositoryCustom cbRepoCustom;
    private final BlockRepositoryCustom crRepoCustom;
    private final ChromaCrBlockRepository crRepo;
    private final ChromaCbBlockRepository cbRepo;
    private final LumaBlockRepository lumaRepo;
    private final BlockMapper mapper;


    public BlockResponse saveBlock(BlockSaveRequest request) {
        BlockBase entity = mapper.toEntity(request);
        BlockBase saved = switch (request.getType()) {
            case LUMA -> lumaRepo.save((LumaBlock) entity);
            case CHROMA_CB -> cbRepo.save((ChromaCbBlock) entity);
            case CHROMA_CR -> crRepo.save((ChromaCrBlock) entity);
        };
        return mapper.toResponse(saved, request.getType());
    }

    @Transactional(readOnly = true)
    public Optional<BlockResponse> getBlockByHash(String hash, BlockType type) {
        byte[] byteHash = HashUtils.hexToBytes(hash);
        Optional<? extends BlockBase> entity = switch (type) {
            case LUMA -> lumaRepo.findByHash(byteHash);
            case CHROMA_CB -> cbRepo.findByHash(byteHash);
            case CHROMA_CR -> crRepo.findByHash(byteHash);
        };
        return entity.map(b -> mapper.toResponse(b, type));
    }

    @Transactional
    public List<String> findMissingHashes(List<String> hexHashes, BlockType type) {
        List<byte[]> hashes = hexHashes.stream().map(HashUtils::hexToBytes).toList();
        Set<String> existing = new HashSet<>();

        for (int i = 0; i < hashes.size(); i += BATCH_SIZE) {
            List<byte[]> batch = hashes.subList(i, Math.min(i + BATCH_SIZE, hashes.size()));
            List<byte[]> found;
            switch (type) {
                case LUMA:
                    found = lumaRepo.findAllByHashIn(batch).stream().map(BlockBase::getHash).toList();
                    byte[][] lumaArr = found.toArray(byte[][]::new);
                    lumaRepoCustom.incrementUsageForHashes(lumaArr);
                    break;
                case CHROMA_CB:
                    found = cbRepo.findAllByHashIn(batch).stream().map(BlockBase::getHash).toList();
                    byte[][] cbArr = found.toArray(byte[][]::new);
                    cbRepoCustom.incrementUsageForHashes(cbArr);
                    break;
                case CHROMA_CR:
                    found = crRepo.findAllByHashIn(batch).stream().map(BlockBase::getHash).toList();
                    byte[][] crArr = found.toArray(byte[][]::new);
                    crRepoCustom.incrementUsageForHashes(crArr);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type: " + type);
            }
            existing.addAll(found.stream().map(HashUtils::bytesToHex).toList());
        }

        return hexHashes.stream().filter(h -> !existing.contains(h)).toList();
    }

    @Transactional
    public List<BlockResponse> saveBlocksBatch(List<BlockSaveRequest> requests) {
        Map<BlockType, List<BlockSaveRequest>> grouped = requests.stream()
                .collect(Collectors.groupingBy(BlockSaveRequest::getType));

        List<BlockResponse> result = new ArrayList<>();
        for (Map.Entry<BlockType, List<BlockSaveRequest>> entry : grouped.entrySet()) {
            BlockType type = entry.getKey();
            List<BlockSaveRequest> list = entry.getValue();
            List<BlockBase> entities = list.stream().map(mapper::toEntity).toList();
            List<BlockBase> saved = switch (type) {
                case LUMA -> new ArrayList<>(lumaRepo.saveAll(entities.stream().map(b -> (LumaBlock) b).toList()));
                case CHROMA_CB ->
                        new ArrayList<>(cbRepo.saveAll(entities.stream().map(b -> (ChromaCbBlock) b).toList()));
                case CHROMA_CR ->
                        new ArrayList<>(crRepo.saveAll(entities.stream().map(b -> (ChromaCrBlock) b).toList()));
            };
            result.addAll(saved.stream().map(b -> mapper.toResponse(b, type)).toList());
        }
        return result;
    }
}
