package legalfasystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "valor_campo_contrato")
public class ValorCampoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;

    @ManyToOne
    @JoinColumn(name = "campo_id", nullable = false)
    private CampoContrato campo;

    @Column(columnDefinition = "TEXT")
    private String valor;
}

