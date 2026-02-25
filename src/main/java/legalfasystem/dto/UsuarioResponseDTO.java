package legalfasystem.dto;

import legalfasystem.enums.UsuarioPerfil;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String login,
    UsuarioPerfil perfil,
    Long funcionarioId,
    Long empresaId,
    Boolean ativo
) {}