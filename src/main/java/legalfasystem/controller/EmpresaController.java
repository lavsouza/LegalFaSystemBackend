package legalfasystem.controller;

import legalfasystem.model.Empresa;
import legalfasystem.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService service;

    @GetMapping
    public List<Empresa> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Empresa> criar(@RequestBody Empresa empresa) {
        return ResponseEntity.ok(service.salvar(empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody Empresa empresa) {
        return ResponseEntity.ok(service.atualizar(id, empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}