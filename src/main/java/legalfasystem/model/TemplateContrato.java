package legalfasystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "template_contrato")
public class TemplateContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

}

