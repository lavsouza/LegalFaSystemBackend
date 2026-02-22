package legalfasystem.dto;

import legalfasystem.enums.UsuarioPerfil;

public record UsuarioLogadoDTO(
    Long id,
    String login,
    UsuarioPerfil perfil,
    FuncionarioDTO funcionario
) {}