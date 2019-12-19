package pl.wietwioorki.to22019.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@Getter
@ToString
public class Reader {
    private Long pesel;
    private String fullName;
    private Date dateOfBirth;
//    private User user;
}
