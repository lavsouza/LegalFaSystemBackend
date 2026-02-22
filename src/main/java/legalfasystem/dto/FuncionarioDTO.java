package legalfasystem.dto;

public record FuncionarioDTO(
    Long id,
    String nome,
    Boolean ativo,
    EmpresaResumoDTO empresa
) {}