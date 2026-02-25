package legalfasystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import legalfasystem.dto.CriarUsuarioDTO;
import legalfasystem.dto.UsuarioAtualizacaoDTO;
import legalfasystem.dto.UsuarioResponseDTO;
import legalfasystem.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * FUNCIONALIDADE 1: Atualizar dados do próprio usuário
     */
    @PutMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> atualizarMeusDados(
            @Valid @RequestBody UsuarioAtualizacaoDTO dto,
            Authentication authentication) {
        
        String loginAtual = authentication.getName();
        UsuarioResponseDTO atualizado = usuarioService.atualizarUsuario(loginAtual, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * FUNCIONALIDADE 2: Criar usuário para a própria empresa (apenas GESTOR)
     */
    @PostMapping("/empresa")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<UsuarioResponseDTO> criarUsuarioNaEmpresa(
            @Valid @RequestBody CriarUsuarioDTO dto,
            Authentication authentication) {
        
        String loginGestor = authentication.getName();
        UsuarioResponseDTO criado = usuarioService.criarUsuarioNaEmpresa(dto, loginGestor);
        return ResponseEntity.ok(criado);
    }

    /**
     * Listar todos os usuários da empresa do gestor
     */
    @GetMapping("/empresa/meus-usuarios")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosDaEmpresa(
            Authentication authentication) {
        
        String loginGestor = authentication.getName();
        List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuariosDaEmpresa(loginGestor);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Deletar usuário da própria empresa
     */
    @DeleteMapping("/{usuarioId}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> deletarUsuario(
            @PathVariable Long usuarioId,
            Authentication authentication) {
        
        String loginGestor = authentication.getName();
        usuarioService.deletarUsuarioDaEmpresa(usuarioId, loginGestor);
        return ResponseEntity.noContent().build();
    }
}