package legalfasystem.controller;

import legalfasystem.dto.RegistroContratoDTO;
import legalfasystem.model.Contrato;
import legalfasystem.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @GetMapping("/{idEmpresa}")
    public List<Contrato> listarContratos(@PathVariable Long idEmpresa){
      return contratoService.listarContratos(idEmpresa);
    };

    @PostMapping("/criar")
    public Contrato criarContrato(@RequestBody RegistroContratoDTO contrato) {
        Contrato c =  new Contrato();



        return contratoService.salvar(c);
    }
}
