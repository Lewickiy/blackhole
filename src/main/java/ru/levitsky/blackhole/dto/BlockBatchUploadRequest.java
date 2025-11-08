package ru.levitsky.blackhole.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockBatchUploadRequest {
    private List<BlockSaveRequest> blocks;
}
