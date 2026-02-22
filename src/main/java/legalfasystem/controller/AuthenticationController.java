package legalfasystem.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import legalfasystem.dto.AuthenticationDTO;
import legalfasystem.dto.EmpresaResumoDTO;
import legalfasystem.dto.FuncionarioDTO;
import legalfasystem.dto.LoginResponseDTO;
import legalfasystem.dto.RegistroUsuarioDTO;
import legalfasystem.dto.UsuarioLogadoDTO;
import legalfasystem.infra.security.TokenService;
import legalfasystem.model.Empresa;
import legalfasystem.model.Funcionario;
import legalfasystem.model.Usuario;
import legalfasystem.repository.EmpresaRepository;
import legalfasystem.repository.UsuarioRepository;
import legalfasystem.service.FuncionarioService;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final FuncionarioService funcionarioService;
    private final EmpresaRepository empresaRepository;

    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UsuarioRepository usuarioRepository,
            FuncionarioService funcionarioService,
            EmpresaRepository  empresaRepository,
            TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.funcionarioService = funcionarioService;
        this.empresaRepository = empresaRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
    var auth = this.authenticationManager.authenticate(usernamePassword);
    
    Usuario usuario = (Usuario) auth.getPrincipal();
    var token = tokenService.generateToken(usuario);

    // Buscar funcionário do usuário
    Funcionario funcionario = funcionarioService.buscarPorUsuario(usuario)
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

    // Montar DTOs
    EmpresaResumoDTO empresaDTO = new EmpresaResumoDTO(
        funcionario.getEmpresa().getId(),
        funcionario.getEmpresa().getRazaoSocial(),
        funcionario.getEmpresa().getCnpj(),
        funcionario.getEmpresa().getEmail(),
        funcionario.getEmpresa().getTelefone(),
        funcionario.getEmpresa().getEndereco()
    );

    FuncionarioDTO funcionarioDTO = new FuncionarioDTO(
        funcionario.getId(),
        funcionario.getNome(),
        funcionario.getAtivo(),
        empresaDTO
    );

    UsuarioLogadoDTO usuarioDTO = new UsuarioLogadoDTO(
        usuario.getId(),
        usuario.getLogin(),
        usuario.getPerfil(),
        funcionarioDTO
    );

    return ResponseEntity.ok(new LoginResponseDTO(token, usuarioDTO));
}

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegistroUsuarioDTO data) {

        if (usuarioRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        // Cria usuário
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.login(), encryptedPassword, data.role());
        usuarioRepository.save(newUser);

        // Busca empresa pelo ID
        Empresa empresa = empresaRepository.findById(data.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        // Cria funcionário
        Funcionario newFunc = new Funcionario(
                newUser,
                empresa,
                data.nomeCompleto(),
                true,
                LocalDateTime.now()
        );

        funcionarioService.salvar(newFunc);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}




/*
joao@hotmail.com
Joao123@

dienelena9@gmail.com
Ravenna1@
*/