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
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "author_id")
    private Long authorId;

    private String fullName;

    @OneToMany(mappedBy = "author")
    private Set<Book> writtenBooks = new HashSet<>();

    public Author(String fullName) {
        this.fullName = fullName;
    }
}
