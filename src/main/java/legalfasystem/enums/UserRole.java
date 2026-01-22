package legalfasystem.enums;

public enum UserRole {

    ADMIN("admin"),
    GESTOR("gestor"),
    ADVOGADO("advogado"),
    ANALISTA("analista"),
    ESTAGIARIO("estagiario");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
