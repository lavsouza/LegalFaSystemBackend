package legalfasystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "campo_contrato")
public class CampoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "secao_id", nullable = false)
    private SecaoContrato secao;

    @Column(nullable = false)
    private String chave; // nome_empresa, data_inicio

    @Column(nullable = false)
    private String label; // Nome da Empresa

    @Column(nullable = false)
    private String tipo; // STRING, DATE, DECIMAL

    @Column(nullable = false)
    private Boolean obrigatorio;
}

