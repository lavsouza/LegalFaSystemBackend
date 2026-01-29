package legalfasystem.service;

import legalfasystem.model.Empresa;
import legalfasystem.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
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
        Empresa empresaExistente = buscarPorId(id);

        empresaExistente.setRazaoSocial(dadosNovos.getRazaoSocial());
        empresaExistente.setEmail(dadosNovos.getEmail());
        empresaExistente.setTelefone(dadosNovos.getTelefone());
        empresaExistente.setEndereco(dadosNovos.getEndereco());
        // Adicione outros campos conforme necessário

        return repository.save(empresaExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Empresa empresa = buscarPorId(id);
        repository.delete(empresa);
    }
}