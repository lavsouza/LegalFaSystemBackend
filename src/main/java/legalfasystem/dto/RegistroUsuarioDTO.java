package legalfasystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import legalfasystem.enums.UsuarioPerfil;
import legalfasystem.model.Empresa;

public record RegistroUsuarioDTO(
        @NotBlank(message = "O login é obrigatório")
        String login,

        @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$",
                message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial"
        )
        @NotBlank(message = "A senha é obrigatória")
        String senha,

        @NotNull(message = "O role é obrigatória")
        UsuarioPerfil role,

        @NotNull(message = "Empresa é obrigatoria")
        Empresa empresa

) {}
