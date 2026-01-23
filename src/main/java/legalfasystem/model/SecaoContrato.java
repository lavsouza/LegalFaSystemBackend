package legalfasystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "secao_contrato")
public class SecaoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private TemplateContrato template;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private Integer ordem;
}

