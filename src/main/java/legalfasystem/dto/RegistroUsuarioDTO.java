package legalfasystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import legalfasystem.enums.UsuarioPerfil;

public record RegistroUsuarioDTO(
        @NotBlank(message = "O login é obrigatório")
        String login,

        @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{6,}$",
                message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial"
        )
        String senha,

        @NotNull(message = "O role é obrigatório")
        UsuarioPerfil role,

        @NotBlank(message = "Nome completo é obrigatório")
        String nomeCompleto,

        @NotNull(message = "O ID da empresa é obrigatório")
        Long empresaId
) {}