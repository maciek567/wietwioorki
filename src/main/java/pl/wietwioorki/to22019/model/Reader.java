package pl.wietwioorki.to22019.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor // ok here because its pesel, not just auto-generated id
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "reader")
public class Reader { // implements Serializable {
//    private static final long serialVersionUID = -2343243243242432341L;
    @Id
    private Long pesel;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "birthdate")
    private Date dateOfBirth;
}
