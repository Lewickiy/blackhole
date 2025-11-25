package ru.levitsky.blackhole.mapper;

import org.mapstruct.Mapper;
import ru.levitsky.blackhole.dto.BlockResponse;
import ru.levitsky.blackhole.dto.BlockSaveRequest;
import ru.levitsky.blackhole.entity.BlockBase;
import ru.levitsky.blackhole.enumeration.BlockType;
import ru.levitsky.blackhole.util.HashUtils;

@Mapper(componentModel = "spring")
public interface BlockMapper {

    default BlockResponse toResponse(BlockBase block, BlockType type) {
        return new BlockResponse(HashUtils.bytesToHex(block.getHash()), block.getData(), block.getCreatedAt(), type);
    }

    default BlockBase toEntity(BlockSaveRequest request) {
        byte[] hash = HashUtils.hexToBytes(request.getHash());
        switch (request.getType()) {
            case LUMA:
                return new ru.levitsky.blackhole.entity.LumaBlock(hash, request.getData());
            case CHROMA_CB:
                return new ru.levitsky.blackhole.entity.ChromaCbBlock(hash, request.getData());
            case CHROMA_CR:
                return new ru.levitsky.blackhole.entity.ChromaCrBlock(hash, request.getData());
            default:
                throw new IllegalArgumentException("Unknown type: " + request.getType());
        }
    }
}
