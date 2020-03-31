package pl.wietwioorki.to22019.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    private String login;

    private String password;

    @Column(name = "role")
    @Convert(converter = RoleConverter.class)
    private Role role;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pesel", referencedColumnName = "pesel")
    private Reader reader;

    private int noLogins;

    private int noBorrowings;

    // notifications settings
    HashMap<ReservationStatus, Boolean> notificationSettings;

    public void incrementNoLogins() { this.noLogins++; }

    public void incrementNoBorrowings() { this.noBorrowings++; }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void changeReadyBook() {
        notificationSettings.replace(ReservationStatus.READY, !notificationSettings.get(ReservationStatus.READY));
    }

    public void changeOverdueBook() {
        notificationSettings.replace(ReservationStatus.OVERDUE, !notificationSettings.get(ReservationStatus.OVERDUE));
    }

    public void changeNewReservation() {
        notificationSettings.replace(ReservationStatus.PENDING, !notificationSettings.get(ReservationStatus.PENDING));
    }

    public void changeBorrowedBook() {
        notificationSettings.replace(ReservationStatus.ACTIVE, !notificationSettings.get(ReservationStatus.ACTIVE));
    }

    public void changeReturnedBook() {
        notificationSettings.replace(ReservationStatus.RETURNED, !notificationSettings.get(ReservationStatus.RETURNED));
    }
}
