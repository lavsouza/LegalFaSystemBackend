package legalfasystem.enums;

public enum UsuarioPerfil {

    ADMIN("ROLE_ADMIN"),
    GESTOR("ROLE_GESTOR"),
    ADVOGADO("ROLE_ADVOGADO"),
    ANALISTA("ROLE_ANALISTA"),
    ESTAGIARIO("ROLE_ESTAGIARIO");

    private final String role;

    UsuarioPerfil(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}