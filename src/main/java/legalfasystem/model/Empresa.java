package legalfasystem.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(nullable = false)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column
    private String endereco;

    @Lob
    @Column(name = "logo_cabecalho")
    private byte[] logoCabecalho;

    @Lob
    @Column(name = "logo_rodape")
    private byte[] logoRodape;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios;

    public Empresa() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public byte[] getLogoCabecalho() {
        return logoCabecalho;
    }

    public void setLogoCabecalho(byte[] logoCabecalho) {
        this.logoCabecalho = logoCabecalho;
    }

    public byte[] getLogoRodape() {
        return logoRodape;
    }

    public void setLogoRodape(byte[] logoRodape) {
        this.logoRodape = logoRodape;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
}