package ru.levitsky.blackhole.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.levitsky.blackhole.dto.BlockBatchUploadRequest;
import ru.levitsky.blackhole.dto.BlockCheckRequest;
import ru.levitsky.blackhole.dto.BlockCheckResponse;
import ru.levitsky.blackhole.dto.BlockResponse;
import ru.levitsky.blackhole.dto.BlockSaveRequest;
import ru.levitsky.blackhole.service.BlockService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/blocks")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    @PostMapping
    public ResponseEntity<BlockResponse> createBlock(@Valid @RequestBody BlockSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blockService.saveBlock(request));
    }

    @GetMapping("/{hash}")
    public ResponseEntity<BlockResponse> getBlockByHash(@PathVariable @NotBlank String hash) {
        return blockService.getBlockByHash(hash)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/check")
    public ResponseEntity<BlockCheckResponse> checkBlocks(@RequestBody @Valid BlockCheckRequest request) {
        List<String> missing = blockService.findMissingHashes(request.getHashes());
        return ResponseEntity.status(HttpStatus.OK).body(new BlockCheckResponse(missing));
    }

    @PostMapping("/upload")
    public ResponseEntity<List<BlockResponse>> uploadBlocks(@RequestBody @Valid BlockBatchUploadRequest request) {
        List<BlockResponse> responses = blockService.saveBlocksBatch(request.getBlocks());
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }
}
