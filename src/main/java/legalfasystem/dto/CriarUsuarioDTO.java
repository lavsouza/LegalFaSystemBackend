package legalfasystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import legalfasystem.enums.UsuarioPerfil;

public record CriarUsuarioDTO(
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    String nome,
    
    @NotBlank(message = "Login é obrigatório")
    @Email(message = "Email inválido")
    String login,
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    String senha,
    
    @NotNull(message = "Perfil é obrigatório")
    UsuarioPerfil perfil,
    
    @NotNull(message = "Empresa ID é obrigatório")
    Long empresaId
) {}