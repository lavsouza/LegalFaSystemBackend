package legalfasystem.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import legalfasystem.enums.StatusContrato;

@Entity
@Table(name = "contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusContrato status = StatusContrato.RASCUNHO;

    @Column(nullable = false)
    private String tipo = "Prestação de Serviços";

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "funcionario_responsavel_id", nullable = false)
    private Funcionario funcionarioResponsavel;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private TemplateContrato template;

    // Armazena dados do contrato em JSON
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados", columnDefinition = "json")
    private Map<String, String> dados;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    public Contrato() {
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusContrato.RASCUNHO;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { 
        this.titulo = titulo;
        this.dataAtualizacao = LocalDateTime.now();
    }
    public StatusContrato getStatus() { return status; }
    public void setStatus(StatusContrato status) { 
        this.status = status;
        this.dataAtualizacao = LocalDateTime.now();
    }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public Funcionario getFuncionarioResponsavel() { return funcionarioResponsavel; }
    public void setFuncionarioResponsavel(Funcionario funcionarioResponsavel) { 
        this.funcionarioResponsavel = funcionarioResponsavel; 
    }
    public TemplateContrato getTemplate() { return template; }
    public void setTemplate(TemplateContrato template) { this.template = template; }
    public Map<String, String> getDados() { return dados; }
    public void setDados(Map<String, String> dados) { 
        this.dados = dados;
        this.dataAtualizacao = LocalDateTime.now();
    }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}