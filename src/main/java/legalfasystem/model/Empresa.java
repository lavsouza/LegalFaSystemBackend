package legalfasystem.model;

import legalfasystem.enums.UserRole;

import java.sql.Blob;
import java.util.List;

public class Empresa {
    private int id;
    private User user;
    private String razaoSocial;
    private String cnpj;
    private String email;
    private String telefone;
    private String endereco;
    private Blob logoCabecalho;
    private Blob logoRodape;
    private List<Funcionario> funcionario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Blob getLogoCabecalho() {
        return logoCabecalho;
    }

    public void setLogoCabecalho(Blob logoCabecalho) {
        this.logoCabecalho = logoCabecalho;
    }

    public Blob getLogoRodape() {
        return logoRodape;
    }

    public void setLogoRodape(Blob logoRodape) {
        this.logoRodape = logoRodape;
    }
}
