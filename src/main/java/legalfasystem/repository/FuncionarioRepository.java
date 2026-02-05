package legalfasystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import legalfasystem.model.Empresa;
import legalfasystem.model.Funcionario;
import legalfasystem.model.Usuario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByUsuario(Usuario usuario);

    List<Funcionario> findByEmpresa(Empresa empresa);

    List<Funcionario> findByAtivoTrue();

    boolean existsByUsuario(Usuario usuario);
}
