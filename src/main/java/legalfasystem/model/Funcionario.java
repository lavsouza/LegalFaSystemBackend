package legalfasystem.model;

import legalfasystem.enums.UsuarioPerfil;

//table("funcionario")
public class Funcionario {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String login;
    private String senha;
    private UsuarioPerfil perfil;
}
