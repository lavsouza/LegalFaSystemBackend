package legalfasystem.dto;

import jakarta.validation.constraints.NotBlank;

public record StatusDTO(
    @NotBlank(message = "Status é obrigatório")
    String status
) {}