package pl.wietwioorki.to22019.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Reader {
    private int pesel;
    private String fullName;
    private Date dateOfBirth;
}
