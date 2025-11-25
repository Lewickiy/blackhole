package ru.levitsky.blackhole.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.levitsky.blackhole.dto.*;
import ru.levitsky.blackhole.enumeration.BlockType;
import ru.levitsky.blackhole.service.BlockService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blocks")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService service;

    @PostMapping
    public ResponseEntity<BlockResponse> createBlock(@RequestBody BlockSaveRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveBlock(req));
    }

    @GetMapping("/{type}/{hash}")
    public ResponseEntity<BlockResponse> getBlock(@PathVariable BlockType type, @PathVariable String hash) {
        return service.getBlockByHash(hash, type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/check")
    public ResponseEntity<BlockCheckResponse> checkBlocks(@RequestBody BlockCheckRequest req, @RequestParam BlockType type) {
        List<String> missing = service.findMissingHashes(req.getHashes(), type);
        return ResponseEntity.ok(new BlockCheckResponse(missing));
    }

    @PostMapping("/upload")
    public ResponseEntity<List<BlockResponse>> uploadBlocks(@RequestBody BlockBatchUploadRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveBlocksBatch(req.getBlocks()));
    }
}
