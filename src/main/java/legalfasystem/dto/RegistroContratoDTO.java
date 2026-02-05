package legalfasystem.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import legalfasystem.enums.StatusContrato;

public record RegistroContratoDTO(

        @Size(min = 10, max = 255, message = "O Titulo do contrato deve ter entre 10 e 255 caracteres")
        @NotNull
        String titulo,

        @Nullable
        StatusContrato status,

        @NotNull //Verificar se existe no banco
        Long idEmpresa,

        @NotNull //Verificar se existe no banco
        Long idFuncionarioResposavel,

        @NotNull //Verificar se existe no banco
        Long idTemplate
) {}
