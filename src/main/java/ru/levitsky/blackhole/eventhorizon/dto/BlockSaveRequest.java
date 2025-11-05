package ru.levitsky.blackhole.eventhorizon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockSaveRequest {

    @NotBlank(message = "Hash cannot be blank")
    private String hash;

    @NotEmpty(message = "Data cannot be empty")
    private byte[] data;
}
