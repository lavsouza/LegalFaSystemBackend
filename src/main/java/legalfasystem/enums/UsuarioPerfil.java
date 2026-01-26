package legalfasystem.enums;

public enum UsuarioPerfil {

    ADMIN("admin"),
    GESTOR("gestor"),
    ADVOGADO("advogado"),
    ANALISTA("analista"),
    ESTAGIARIO("estagiario");

    private final String perfil;

    UsuarioPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil() {
        return perfil;
    }
}