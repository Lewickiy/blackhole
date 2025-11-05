package ru.levitsky.blackhole.core.mapper;

import org.mapstruct.Mapper;
import ru.levitsky.blackhole.eventhorizon.dto.BlockResponse;
import ru.levitsky.blackhole.eventhorizon.dto.BlockSaveRequest;
import ru.levitsky.blackhole.singularity.entity.Block;

@Mapper(componentModel = "spring")
public interface BlockMapper {
    BlockResponse toResponse(Block block);

    Block toEntity(BlockSaveRequest request);
}
