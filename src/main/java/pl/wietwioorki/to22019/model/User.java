package pl.wietwioorki.to22019.model;

import lombok.*;

import javax.persistence.*;

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
    private boolean readyBookNotification = true;
    private  boolean overdueBookNotification = true;
    private boolean newReservationNotification = false;
    private boolean borrowedBookNotification = false;
    private  boolean returnedBookNotification= false;

    public void incrementNoLogins() { this.noLogins++; }

    public int getNoLogins() { return noLogins; }

    public void incrementNoBorrowings() { this.noBorrowings++; }

    public int getNoBorrowings() { return noBorrowings; }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void changeReadyBook() {
        readyBookNotification = !readyBookNotification;
    }

    public void changeOverdueBook() {
        overdueBookNotification = !overdueBookNotification;
    }

    public void changeNewReservation() {
        newReservationNotification = !newReservationNotification;
    }

    public void changeBorrowedBook() {
        borrowedBookNotification = !borrowedBookNotification;
    }

    public void changeReturnedBook() {
        returnedBookNotification = !returnedBookNotification;
    }

    public boolean isReadyBookNotification() {
        return readyBookNotification;
    }

    public boolean isOverdueBookNotification() {
        return overdueBookNotification;
    }

    public boolean isNewReservationNotification() {
        return newReservationNotification;
    }

    public boolean isBorrowedBookNotification() {
        return borrowedBookNotification;
    }

    public boolean isReturnedBookNotification() {
        return returnedBookNotification;
    }
}
