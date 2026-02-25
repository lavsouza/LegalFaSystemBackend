package legalfasystem.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ContratoDTO(
    Long id,
    String titulo,
    String status,
    String tipo,
    LocalDateTime dataCriacao,
    LocalDateTime dataAtualizacao,
    String criadoPor,
    Map<String, String> dados
) {}
