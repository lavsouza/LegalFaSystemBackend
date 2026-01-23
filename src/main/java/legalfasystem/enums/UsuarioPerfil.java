package legalfasystem.enums;

//table("usuarioperfil")
public enum UsuarioPerfil {

    ADMIN("admin"),
    GESTOR("gestor"),
    ADVOGADO("advogado"),
    ANALISTA("analista"),
    ESTAGIARIO("estagiario");

    private final String role;

    UsuarioPerfil(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
