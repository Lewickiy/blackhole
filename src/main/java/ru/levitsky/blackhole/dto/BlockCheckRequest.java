package ru.levitsky.blackhole.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.levitsky.blackhole.enumeration.BlockType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockCheckRequest {
    private List<String> hashes;
    private BlockType type;
}
