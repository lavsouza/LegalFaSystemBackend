package legalfasystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import legalfasystem.dto.CriarUsuarioDTO;
import legalfasystem.dto.UsuarioAtualizacaoDTO;
import legalfasystem.dto.UsuarioResponseDTO;
import legalfasystem.model.Empresa;
import legalfasystem.model.Funcionario;
import legalfasystem.model.Usuario;
import legalfasystem.repository.EmpresaRepository;
import legalfasystem.repository.FuncionarioRepository;
import legalfasystem.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            FuncionarioRepository funcionarioRepository,
            EmpresaRepository empresaRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * FUNCIONALIDADE 1: Atualizar dados do próprio usuário
     */
    @Transactional
    public UsuarioResponseDTO atualizarUsuario(String loginAtual, UsuarioAtualizacaoDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(loginAtual)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Funcionario funcionario = funcionarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        // Atualizar nome do funcionário
        if (dto.nome() != null && !dto.nome().isBlank()) {
            funcionario.setNome(dto.nome());
        }

        // Atualizar login (email)
        if (dto.login() != null && !dto.login().isBlank()) {
            // Verificar se o novo login já existe (e não é o mesmo usuário)
            usuarioRepository.findByLogin(dto.login()).ifPresent(u -> {
                if (!u.getId().equals(usuario.getId())) {
                    throw new RuntimeException("Este login já está em uso");
                }
            });
            usuario.setLogin(dto.login());
        }

        // Atualizar senha (se fornecida)
        if (dto.senha() != null && !dto.senha().isBlank()) {
            String senhaCriptografada = passwordEncoder.encode(dto.senha());
            usuario.setSenha(senhaCriptografada);
        }

        funcionarioRepository.save(funcionario);
        usuarioRepository.save(usuario);

        return toDTO(usuario, funcionario);
    }

    /**
     * FUNCIONALIDADE 2: Criar usuário na própria empresa (apenas GESTOR)
     */
    @Transactional
    public UsuarioResponseDTO criarUsuarioNaEmpresa(CriarUsuarioDTO dto, String loginGestor) {
        // Buscar o gestor
        Usuario gestor = usuarioRepository.findByLogin(loginGestor)
                .orElseThrow(() -> new RuntimeException("Gestor não encontrado"));

        Funcionario funcionarioGestor = funcionarioRepository.findByUsuario(gestor)
                .orElseThrow(() -> new RuntimeException("Funcionário gestor não encontrado"));

        Long empresaIdGestor = funcionarioGestor.getEmpresa().getId();

        // VALIDAÇÃO: Só pode criar usuário na própria empresa
        if (!dto.empresaId().equals(empresaIdGestor)) {
            throw new RuntimeException("Você só pode criar usuários para a sua própria empresa");
        }

        // Verificar se login já existe
        if (usuarioRepository.findByLogin(dto.login()).isPresent()) {
            throw new RuntimeException("Este login já está em uso");
        }

        // Buscar empresa
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        // Criar usuário
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(dto.login());
        novoUsuario.setSenha(passwordEncoder.encode(dto.senha()));
        novoUsuario.setPerfil(dto.perfil());

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // Criar funcionário
        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(dto.nome());
        novoFuncionario.setEmpresa(empresa);
        novoFuncionario.setUsuario(usuarioSalvo);
        novoFuncionario.setAtivo(true);

        Funcionario funcionarioSalvo = funcionarioRepository.save(novoFuncionario);

        return toDTO(usuarioSalvo, funcionarioSalvo);
    }

    /**
     * Listar todos os usuários da empresa do gestor
     */
    public List<UsuarioResponseDTO> listarUsuariosDaEmpresa(String loginGestor) {
        Usuario gestor = usuarioRepository.findByLogin(loginGestor)
                .orElseThrow(() -> new RuntimeException("Gestor não encontrado"));

        Funcionario funcionarioGestor = funcionarioRepository.findByUsuario(gestor)
                .orElseThrow(() -> new RuntimeException("Funcionário gestor não encontrado"));

        Long empresaId = funcionarioGestor.getEmpresa().getId();

        List<Funcionario> funcionarios = funcionarioRepository.findByEmpresaId(empresaId);

        return funcionarios.stream()
                .map(f -> toDTO(f.getUsuario(), f))
                .collect(Collectors.toList());
    }

    /**
     * Deletar usuário da própria empresa
     */
    @Transactional
    public void deletarUsuarioDaEmpresa(Long usuarioId, String loginGestor) {
        Usuario gestor = usuarioRepository.findByLogin(loginGestor)
                .orElseThrow(() -> new RuntimeException("Gestor não encontrado"));

        Funcionario funcionarioGestor = funcionarioRepository.findByUsuario(gestor)
                .orElseThrow(() -> new RuntimeException("Funcionário gestor não encontrado"));

        Usuario usuarioParaDeletar = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Funcionario funcionarioParaDeletar = funcionarioRepository.findByUsuario(usuarioParaDeletar)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        // VALIDAÇÃO: Só pode deletar usuários da própria empresa
        if (!funcionarioParaDeletar.getEmpresa().getId().equals(funcionarioGestor.getEmpresa().getId())) {
            throw new RuntimeException("Você só pode deletar usuários da sua própria empresa");
        }

        // VALIDAÇÃO: Não pode deletar a si mesmo
        if (usuarioParaDeletar.getId().equals(gestor.getId())) {
            throw new RuntimeException("Você não pode deletar sua própria conta");
        }

        funcionarioRepository.delete(funcionarioParaDeletar);
        usuarioRepository.delete(usuarioParaDeletar);
    }

    /**
     * Converter para DTO
     */
    private UsuarioResponseDTO toDTO(Usuario usuario, Funcionario funcionario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                funcionario.getNome(),
                usuario.getLogin(),
                usuario.getPerfil(),
                funcionario.getId(),
                funcionario.getEmpresa().getId(),
                funcionario.getAtivo()
        );
    }
}