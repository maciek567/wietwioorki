package pl.wietwioorki.to22019.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor // ok here because its pesel, not just auto-generated id
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "reader")
public class Reader {
    @Id
    private Long pesel;

    @Column(name = "full_name")
    private String fullName;

    @Setter
    @OneToOne(mappedBy = "reader")
    private User user;

    public Reader(Long pesel, String fullName) {
        this.pesel = pesel;
        this.fullName = fullName;
    }
}
