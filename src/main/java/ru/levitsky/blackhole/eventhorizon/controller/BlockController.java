package ru.levitsky.blackhole.eventhorizon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.levitsky.blackhole.core.service.BlockService;
import ru.levitsky.blackhole.singularity.entity.Block;

import java.util.Optional;

@RestController
@RequestMapping("/block")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    @PostMapping
    public ResponseEntity<Block> createBlock(@RequestBody Block block) {
        Block saved = blockService.saveBlock(block);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{hash}")
    public ResponseEntity<Block> getBlock(@PathVariable String hash) {
        Optional<Block> block = blockService.getBlockByHash(hash);
        return block.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
