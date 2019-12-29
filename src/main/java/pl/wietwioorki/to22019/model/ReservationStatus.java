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

    public static ReservationStatus of(String description) {
        for (ReservationStatus reservationStatus : ReservationStatus.values()) {
            if (reservationStatus.getDescription().equals(description)) {
                return reservationStatus;
            }
        }
        throw new UnsupportedOperationException("Didn't find status corresponding to given description");
    }
}
