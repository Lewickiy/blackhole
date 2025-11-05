package ru.levitsky.blackhole.eventhorizon.controller;

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
import ru.levitsky.blackhole.core.service.BlockService;
import ru.levitsky.blackhole.eventhorizon.dto.BlockResponse;
import ru.levitsky.blackhole.eventhorizon.dto.BlockSaveRequest;

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
}
