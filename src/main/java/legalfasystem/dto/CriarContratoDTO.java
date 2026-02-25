package legalfasystem.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarContratoDTO(
    @NotBlank(message = "Título é obrigatório")
    String titulo,
    
    @NotNull Long empresaId,
    @NotNull Long funcionarioResponsavelId,
    @NotNull Long templateId,
    @NotNull Map<String, String> dados
) {}