package pl.wietwioorki.to22019.model;

public enum ReservationStatus {
    P("pending"),
    R("ready"),
    A("active"),
    O("overdue"),
    E("executed");

    private String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
