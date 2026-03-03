package legalfasystem.repository;

import legalfasystem.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
    // Busca case insensitive
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.login) = LOWER(:login)")
    Optional<Usuario> findByLoginIgnoreCase(@Param("login") String login);
    
    boolean existsByLogin(String login);
}
