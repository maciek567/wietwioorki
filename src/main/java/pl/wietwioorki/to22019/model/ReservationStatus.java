package pl.wietwioorki.to22019.model;

public enum ReservationStatus {
    A("active"),
    O("overdue"),
    P("pending");

    private String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
