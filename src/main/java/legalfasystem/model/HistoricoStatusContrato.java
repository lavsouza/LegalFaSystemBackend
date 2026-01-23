package legalfasystem.model;

import jakarta.persistence.*;
import legalfasystem.enums.StatusContrato;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_status_contrato")
public class HistoricoStatusContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_anterior", nullable = false)
    private StatusContrato statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_novo", nullable = false)
    private StatusContrato statusNovo;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime dataAlteracao;

    @ManyToOne
    @JoinColumn(name = "alterado_por")
    private Usuario alteradoPor;

    @Column(length = 500)
    private String observacao;


    public HistoricoStatusContrato() {
        this.dataAlteracao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public StatusContrato getStatusAnterior() {
        return statusAnterior;
    }

    public void setStatusAnterior(StatusContrato statusAnterior) {
        this.statusAnterior = statusAnterior;
    }

    public StatusContrato getStatusNovo() {
        return statusNovo;
    }

    public void setStatusNovo(StatusContrato statusNovo) {
        this.statusNovo = statusNovo;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Usuario getAlteradoPor() {
        return alteradoPor;
    }

    public void setAlteradoPor(Usuario alteradoPor) {
        this.alteradoPor = alteradoPor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
