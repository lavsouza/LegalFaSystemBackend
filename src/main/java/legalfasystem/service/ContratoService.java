package legalfasystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import legalfasystem.dto.ContratoDTO;
import legalfasystem.dto.CriarContratoDTO;
import legalfasystem.enums.StatusContrato;
import legalfasystem.model.Contrato;
import legalfasystem.model.Empresa;
import legalfasystem.model.Funcionario;
import legalfasystem.model.TemplateContrato;
import legalfasystem.repository.ContratoRepository;
import legalfasystem.repository.EmpresaRepository;
import legalfasystem.repository.FuncionarioRepository;
import legalfasystem.repository.TemplateContratoRepository;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final EmpresaRepository empresaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final TemplateContratoRepository templateRepository;

    public ContratoService(
            ContratoRepository contratoRepository,
            EmpresaRepository empresaRepository,
            FuncionarioRepository funcionarioRepository,
            TemplateContratoRepository templateRepository) {
        this.contratoRepository = contratoRepository;
        this.empresaRepository = empresaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.templateRepository = templateRepository;
    }

    public List<ContratoDTO> listarPorEmpresa(Long empresaId) {
        return contratoRepository.findByEmpresaId(empresaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
public ContratoDTO atualizarStatus(Long id, StatusContrato status) {
    Contrato contrato = contratoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
    
    contrato.setStatus(status);
    contrato = contratoRepository.save(contrato);
    
    return toDTO(contrato);
}
    public ContratoDTO buscarPorId(Long id) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
        return toDTO(contrato);
    }

    @Transactional
    public ContratoDTO criar(CriarContratoDTO dto) {
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        
        Funcionario funcionario = funcionarioRepository.findById(dto.funcionarioResponsavelId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        
        TemplateContrato template = templateRepository.findById(dto.templateId())
                .orElseThrow(() -> new RuntimeException("Template não encontrado"));

        Contrato contrato = new Contrato();
        contrato.setTitulo(dto.titulo());
        contrato.setEmpresa(empresa);
        contrato.setFuncionarioResponsavel(funcionario);
        contrato.setTemplate(template);
        contrato.setDados(dto.dados());

        Contrato saved = contratoRepository.save(contrato);
        return toDTO(saved);
    }

    @Transactional
    public ContratoDTO atualizar(Long id, CriarContratoDTO dto) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
        
        contrato.setTitulo(dto.titulo());
        contrato.setDados(dto.dados());
        
        Contrato updated = contratoRepository.save(contrato);
        return toDTO(updated);
    }

    @Transactional
    public void deletar(Long id) {
        contratoRepository.deleteById(id);
    }

    private ContratoDTO toDTO(Contrato contrato) {
        return new ContratoDTO(
            contrato.getId(),
            contrato.getTitulo(),
            contrato.getStatus().toString(),
            contrato.getTipo(),
            contrato.getDataCriacao(),
            contrato.getDataAtualizacao(),
            contrato.getFuncionarioResponsavel().getNome(),
            contrato.getDados()
        );
    }
    
}