package ru.levitsky.blackhole.mapper;

import org.mapstruct.Mapper;
import ru.levitsky.blackhole.dto.BlockResponse;
import ru.levitsky.blackhole.dto.BlockSaveRequest;
import ru.levitsky.blackhole.entity.Block;

@Mapper(componentModel = "spring")
public interface BlockMapper {
    BlockResponse toResponse(Block block);

    Block toEntity(BlockSaveRequest request);
}
