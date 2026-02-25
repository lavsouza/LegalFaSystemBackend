package legalfasystem.controller;

import legalfasystem.dto.EmpresaAtualizacaoDTO;
import legalfasystem.dto.EmpresaResponseDTO;
import legalfasystem.model.Empresa;
import legalfasystem.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService service;

    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listar() {
        List<Empresa> empresas = service.listarTodos();
        List<EmpresaResponseDTO> dtos = empresas.stream()
                .map(EmpresaResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscar(@PathVariable Long id) {
        Empresa empresa = service.buscarPorId(id);
        return ResponseEntity.ok(new EmpresaResponseDTO(empresa));
    }

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> criar(@RequestBody Empresa empresa) {
        Empresa salva = service.salvar(empresa);
        return ResponseEntity.ok(new EmpresaResponseDTO(salva));
    }

    /**
     * Endpoint para atualização completa da empresa (incluindo logos)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EmpresaAtualizacaoDTO dto) {
        try {
            System.out.println("=== CONTROLLER: PUT /api/empresas/" + id + " ===");
            System.out.println("DTO recebido: " + dto);
            
            Empresa atualizada = service.atualizarComDTO(id, dto);
            EmpresaResponseDTO response = new EmpresaResponseDTO(atualizada);
            
            System.out.println("CONTROLLER: Respondendo com DTO");
            
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(response);
                    
        } catch (Exception e) {
            System.err.println("CONTROLLER: Erro ao atualizar: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar empresa: " + e.getMessage());
        }
    }
    
    /**
     * Endpoint alternativo para manter compatibilidade com o frontend antigo
     * (caso precise)
     */
    @PutMapping("/{id}/simples")
    public ResponseEntity<EmpresaResponseDTO> atualizarSimples(@PathVariable Long id, @RequestBody Empresa empresa) {
        Empresa atualizada = service.atualizar(id, empresa);
        return ResponseEntity.ok(new EmpresaResponseDTO(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}