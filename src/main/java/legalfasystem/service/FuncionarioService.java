package legalfasystem.service;

import legalfasystem.model.Funcionario;
import org.springframework.stereotype.Service;

import legalfasystem.repository.FuncionarioRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<Funcionario> listarTodos() {
        return repository.findAll();
    }

    public Funcionario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario) {
        return repository.save(funcionario);
    }

    @Transactional
    public Funcionario atualizar(Long id, Funcionario dadosNovos) {
        Funcionario funcionario = buscarPorId(id);

        funcionario.setNome(dadosNovos.getNome());
        funcionario.setAtivo(dadosNovos.getAtivo());
        funcionario.setEmpresa(dadosNovos.getEmpresa());
        funcionario.setUsuario(dadosNovos.getUsuario());

        return repository.save(funcionario);
    }

    @Transactional
    public void deletar(Long id) {
        Funcionario funcionario = buscarPorId(id);
        repository.delete(funcionario);
    }
}