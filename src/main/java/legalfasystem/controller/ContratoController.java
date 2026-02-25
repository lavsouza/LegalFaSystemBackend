package legalfasystem.controller;

import jakarta.validation.Valid;
import legalfasystem.dto.ContratoDTO;
import legalfasystem.dto.CriarContratoDTO;
import legalfasystem.dto.StatusDTO;
import legalfasystem.enums.StatusContrato;
import legalfasystem.model.Contrato;
import legalfasystem.service.ContratoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping("/empresa/{empresaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'ADVOGADO', 'ANALISTA', 'ESTAGIARIO')")
    public ResponseEntity<List<ContratoDTO>> listarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(contratoService.listarPorEmpresa(empresaId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'ADVOGADO', 'ANALISTA', 'ESTAGIARIO')")
    public ResponseEntity<ContratoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.buscarPorId(id));
    }
@PatchMapping("/{id}/status")
@PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'ADVOGADO', 'ANALISTA')")
public ResponseEntity<ContratoDTO> atualizarStatus(
        @PathVariable Long id,
        @RequestBody @Valid StatusDTO statusDTO) {
    
    // Converte string para enum
    StatusContrato status = StatusContrato.valueOf(statusDTO.status());
    ContratoDTO contratoAtualizado = contratoService.atualizarStatus(id, status);
    
    return ResponseEntity.ok(contratoAtualizado);
}
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'ADVOGADO', 'ANALISTA')")
    public ResponseEntity<ContratoDTO> criar(@RequestBody @Valid CriarContratoDTO dto) {
        return ResponseEntity.ok(contratoService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'ADVOGADO', 'ANALISTA')")
    public ResponseEntity<ContratoDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid CriarContratoDTO dto) {
        return ResponseEntity.ok(contratoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contratoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}