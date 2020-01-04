package pl.wietwioorki.to22019.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "genre_id")
    private Long id;

    private String name;
    private String description;

//    @ManyToMany(mappedBy = "genres")
//    private Set<Book> books = new HashSet<>();


    @OneToMany(mappedBy = "genre")
    private Set<Book> books = new HashSet<>();

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }
}