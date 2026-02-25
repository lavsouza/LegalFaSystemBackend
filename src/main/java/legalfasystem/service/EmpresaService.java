package legalfasystem.service;

import legalfasystem.dto.EmpresaAtualizacaoDTO;
import legalfasystem.model.Empresa;
import legalfasystem.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;

    public List<Empresa> listarTodos() {
        return repository.findAll();
    }

    public Empresa buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + id));
    }

    @Transactional
    public Empresa salvar(Empresa empresa) {
        if (repository.existsByCnpj(empresa.getCnpj())) {
            throw new RuntimeException("CNPJ já cadastrado");
        }
        return repository.save(empresa);
    }

    @Transactional
    public Empresa atualizar(Long id, Empresa dadosNovos) {
        System.out.println("=== INÍCIO ATUALIZAÇÃO (Entidade) ===");
        System.out.println("1. Buscando empresa ID: " + id);
        
        Empresa empresaExistente = buscarPorId(id);
        System.out.println("2. Empresa encontrada: " + empresaExistente);
        
        System.out.println("3. Atualizando campos:");
        
        // Atualizar campos de texto
        if (dadosNovos.getRazaoSocial() != null) {
            empresaExistente.setRazaoSocial(dadosNovos.getRazaoSocial());
        }
        
        if (dadosNovos.getEmail() != null) {
            empresaExistente.setEmail(dadosNovos.getEmail());
        }
        
        if (dadosNovos.getTelefone() != null) {
            empresaExistente.setTelefone(dadosNovos.getTelefone());
        }
        
        if (dadosNovos.getEndereco() != null) {
            empresaExistente.setEndereco(dadosNovos.getEndereco());
        }
        
        // Atualizar logos apenas se foram enviadas
        if (dadosNovos.getLogoCabecalho() != null) {
            empresaExistente.setLogoCabecalho(dadosNovos.getLogoCabecalho());
            System.out.println("Logo Cabeçalho atualizada (tamanho: " + dadosNovos.getLogoCabecalho().length + " bytes)");
        }
        
        if (dadosNovos.getLogoRodape() != null) {
            empresaExistente.setLogoRodape(dadosNovos.getLogoRodape());
            System.out.println("Logo Rodapé atualizada (tamanho: " + dadosNovos.getLogoRodape().length + " bytes)");
        }
        
        System.out.println("4. Salvando no banco...");
        Empresa salva = repository.save(empresaExistente);
        System.out.println("5. Salvo com sucesso! ID: " + salva.getId());
        
        System.out.println("=== FIM ATUALIZAÇÃO ===\n");
        return salva;
    }
    
    /**
     * Método específico para atualização com DTO (incluindo logos em Base64)
     */
    @Transactional
    public Empresa atualizarComDTO(Long id, EmpresaAtualizacaoDTO dto) {
        System.out.println("=== INÍCIO ATUALIZAÇÃO (DTO) ===");
        System.out.println("1. Buscando empresa ID: " + id);
        
        Empresa empresaExistente = buscarPorId(id);
        System.out.println("2. Empresa encontrada: " + empresaExistente);
        
        System.out.println("3. Atualizando campos do DTO:");
        
        // Atualizar campos de texto
        if (dto.getRazaoSocial() != null && !dto.getRazaoSocial().trim().isEmpty()) {
            empresaExistente.setRazaoSocial(dto.getRazaoSocial());
        }
        
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            empresaExistente.setEmail(dto.getEmail());
        }
        
        if (dto.getTelefone() != null) {
            empresaExistente.setTelefone(dto.getTelefone());
        }
        
        if (dto.getEndereco() != null) {
            empresaExistente.setEndereco(dto.getEndereco());
        }
        
        // Processar Logo Cabeçalho (se veio no DTO)
        if (dto.getLogoCabecalhoBase64() != null && !dto.getLogoCabecalhoBase64().trim().isEmpty()) {
            try {
                byte[] logoBytes = Base64.getDecoder().decode(dto.getLogoCabecalhoBase64());
                empresaExistente.setLogoCabecalho(logoBytes);
                System.out.println("Logo Cabeçalho atualizada: " + logoBytes.length + " bytes");
            } catch (IllegalArgumentException e) {
                System.err.println("Erro ao decodificar Logo Cabeçalho Base64: " + e.getMessage());
            }
        }
        
        // Processar Logo Rodapé (se veio no DTO)
        if (dto.getLogoRodapeBase64() != null && !dto.getLogoRodapeBase64().trim().isEmpty()) {
            try {
                byte[] logoBytes = Base64.getDecoder().decode(dto.getLogoRodapeBase64());
                empresaExistente.setLogoRodape(logoBytes);
                System.out.println("Logo Rodapé atualizada: " + logoBytes.length + " bytes");
            } catch (IllegalArgumentException e) {
                System.err.println("Erro ao decodificar Logo Rodapé Base64: " + e.getMessage());
            }
        }
        
        System.out.println("4. Salvando no banco...");
        Empresa salva = repository.save(empresaExistente);
        System.out.println("5. Salvo com sucesso! ID: " + salva.getId());
        
        System.out.println("=== FIM ATUALIZAÇÃO DTO ===\n");
        return salva;
    }

    @Transactional
    public void deletar(Long id) {
        Empresa empresa = buscarPorId(id);
        repository.delete(empresa);
    }
}