package legalfasystem.dto;

public record EmpresaResumoDTO(
    Long id,
    String razaoSocial,
    String cnpj,
    String emailCorporativo,
    String telefone,
    String endereco
) {}