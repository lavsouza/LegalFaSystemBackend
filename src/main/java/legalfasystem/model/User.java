package legalfasystem.model;

import legalfasystem.enums.UserRole;

// table(name = "USER_ACCOUNT")
public class User {

    private String id;
    private String login;
    private String passoword;
    private UserRole userRole;
}
