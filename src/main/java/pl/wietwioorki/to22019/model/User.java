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

    public void incrementNoLogins() {
        this.noLogins++;
    }

    public int getNoLogins() {
        return noLogins;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
