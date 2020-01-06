package pl.wietwioorki.to22019.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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
    HashMap<String, Boolean> notificationSettings;

    public void incrementNoLogins() { this.noLogins++; }

    public void incrementNoBorrowings() { this.noBorrowings++; }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void changeReadyBook() {
        notificationSettings.replace("readyBookNotification", !notificationSettings.get("readyBookNotification"));
    }

    public void changeOverdueBook() {
        notificationSettings.replace("overdueBookNotification", !notificationSettings.get("overdueBookNotification"));
    }

    public void changeNewReservation() {
        notificationSettings.replace("newReservationNotification", !notificationSettings.get("newReservationNotification"));
    }

    public void changeBorrowedBook() {
        notificationSettings.replace("borrowedBookNotification", !notificationSettings.get("borrowedBookNotification"));
    }

    public void changeReturnedBook() {
        notificationSettings.replace("returnedBookNotification", !notificationSettings.get("returnedBookNotification"));
    }
}
