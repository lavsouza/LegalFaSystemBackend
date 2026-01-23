package legalfasystem.model;

import legalfasystem.enums.StatusContrato;

import java.util.Date;

//table("contrato")
public class Contrato {
    private int id;
    private StatusContrato statusContrato;
    private Funcionario funcionarioResponsavel;
    private Date dataCriacao;
}
