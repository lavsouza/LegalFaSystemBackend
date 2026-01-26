package legalfasystem.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RoleHierarchyTest {

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Test
    @DisplayName("Deve validar a cadeia de herança das roles")
    void testRoleHierarchyChain() {
        List<GrantedAuthority> adminAuthorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));

        var reachable = roleHierarchy.getReachableGrantedAuthorities(adminAuthorities)
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        assertTrue(reachable.contains("ROLE_GESTOR"));
        assertTrue(reachable.contains("ROLE_ADVOGADO"));
        assertTrue(reachable.contains("ROLE_ANALISTA"));
        assertTrue(reachable.contains("ROLE_ESTAGIARIO"));
    }
}
