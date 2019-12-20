package pl.wietwioorki.to22019.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.wietwioorki.to22019.dao.ReaderDAO;

@AllArgsConstructor
@Getter
@ToString
public class User {
    private long id;
    private String login;
    private String password;
    private Role role;
    private long pesel;

    public Reader getReader(){
        return ReaderDAO.findByPesel(pesel);
    }

    public boolean checkPassword(String password){
        return (password.equals(password));
    }
}
