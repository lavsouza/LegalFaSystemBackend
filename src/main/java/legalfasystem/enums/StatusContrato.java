package legalfasystem.enums;

//table("status contrato")
public enum StatusContrato {

    STATUS("status");

    private final String status;

    StatusContrato(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
