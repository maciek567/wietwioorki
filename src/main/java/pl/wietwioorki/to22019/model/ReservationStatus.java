package pl.wietwioorki.to22019.model;

public enum ReservationStatus {
    PENDING("pending"),
    READY("ready"),
    ACTIVE("active"),
    OVERDUE("overdue"),
    RETURNED("returned");

    private String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
