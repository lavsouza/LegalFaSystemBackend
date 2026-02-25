package legalfasystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UsuarioAtualizacaoDTO(
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    String nome,
    
    @Email(message = "Email inválido")
    String login,
    
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    String senha  // Opcional
) {}