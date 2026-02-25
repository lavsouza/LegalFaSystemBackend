package legalfasystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpresaAtualizacaoDTO {
    
    @JsonProperty("razaoSocial")
    private String razaoSocial;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("telefone")
    private String telefone;
    
    @JsonProperty("endereco")
    private String endereco;
    
    @JsonProperty("logoCabecalho")
    private String logoCabecalhoBase64; // Recebe base64 string
    
    @JsonProperty("logoRodape")
    private String logoRodapeBase64; // Recebe base64 string
    
    // Construtores
    public EmpresaAtualizacaoDTO() {}
    
    public EmpresaAtualizacaoDTO(String razaoSocial, String email, String telefone, 
                                 String endereco, String logoCabecalhoBase64, String logoRodapeBase64) {
        this.razaoSocial = razaoSocial;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.logoCabecalhoBase64 = logoCabecalhoBase64;
        this.logoRodapeBase64 = logoRodapeBase64;
    }
    
    // Getters e Setters
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public String getLogoCabecalhoBase64() { return logoCabecalhoBase64; }
    public void setLogoCabecalhoBase64(String logoCabecalhoBase64) { this.logoCabecalhoBase64 = logoCabecalhoBase64; }
    
    public String getLogoRodapeBase64() { return logoRodapeBase64; }
    public void setLogoRodapeBase64(String logoRodapeBase64) { this.logoRodapeBase64 = logoRodapeBase64; }
    
    @Override
    public String toString() {
        return "EmpresaAtualizacaoDTO{" +
                "razaoSocial='" + razaoSocial + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", temLogoCabecalho=" + (logoCabecalhoBase64 != null) +
                ", temLogoRodape=" + (logoRodapeBase64 != null) +
                '}';
    }
}