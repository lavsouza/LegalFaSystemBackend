package legalfasystem.dto;

import legalfasystem.model.Empresa;
import java.util.Base64;

public class EmpresaResponseDTO {
    private Long id;
    private String razaoSocial;
    private String cnpj;
    private String email;
    private String telefone;
    private String endereco;
    private String logoCabecalho; // Base64 string para enviar ao frontend
    private String logoRodape;     // Base64 string para enviar ao frontend
    
    public EmpresaResponseDTO() {}
    
    public EmpresaResponseDTO(Empresa empresa) {
        this.id = empresa.getId();
        this.razaoSocial = empresa.getRazaoSocial();
        this.cnpj = empresa.getCnpj();
        this.email = empresa.getEmail();
        this.telefone = empresa.getTelefone();
        this.endereco = empresa.getEndereco();
        
        // Converter byte[] para Base64 se existir
        if (empresa.getLogoCabecalho() != null) {
            this.logoCabecalho = Base64.getEncoder().encodeToString(empresa.getLogoCabecalho());
        }
        
        if (empresa.getLogoRodape() != null) {
            this.logoRodape = Base64.getEncoder().encodeToString(empresa.getLogoRodape());
        }
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public String getLogoCabecalho() { return logoCabecalho; }
    public void setLogoCabecalho(String logoCabecalho) { this.logoCabecalho = logoCabecalho; }
    
    public String getLogoRodape() { return logoRodape; }
    public void setLogoRodape(String logoRodape) { this.logoRodape = logoRodape; }
}